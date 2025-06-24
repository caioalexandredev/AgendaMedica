package br.edu.ifto.sistemaconsulta.controller;

import br.edu.ifto.sistemaconsulta.model.entity.*;
import br.edu.ifto.sistemaconsulta.model.enums.StatusHorarioAgendaEnum;
import br.edu.ifto.sistemaconsulta.model.enums.TipoAgendaGerarEnum;
import br.edu.ifto.sistemaconsulta.model.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@Controller
@RequestMapping("agenda")
public class AgendaController {
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    AgendaGerarRepository agendaGerarRepository;

    @Autowired
    HorarioAgendaRepository horarioAgendaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @GetMapping("/listar")
    public ModelAndView listar(
            @RequestParam(value = "medico", required = false) String medico,
            @RequestParam(value = "data", required = false) String data,
            @RequestParam(value = "status", required = false) StatusHorarioAgendaEnum statusHorarioAgendaEnum,
            ModelMap model
    ) {

        LocalDate dataFormat = data == null || data.isEmpty() ? LocalDate.now() : LocalDate.parse(data);
        statusHorarioAgendaEnum = statusHorarioAgendaEnum == null ? StatusHorarioAgendaEnum.DISPONIVEL : statusHorarioAgendaEnum;

        model.addAttribute("data", dataFormat);
        model.addAttribute("status", statusHorarioAgendaEnum);
        model.addAttribute("medico", medico);

        model.addAttribute("medicos", medicoRepository.medicos());
        model.addAttribute("statusOptions", StatusHorarioAgendaEnum.values());

        model.addAttribute("horariosAgenda", new ArrayList<HorarioAgenda>());

        Medico medicoConsulta = new Medico();

        if(medico != null && !medico.isBlank()) {
            medicoConsulta = medicoRepository.medico(Long.valueOf(medico));
        }

        List<HorarioAgenda> horariosAgenda = horarioAgendaRepository.search(medicoConsulta.getId(), dataFormat, statusHorarioAgendaEnum);
        model.addAttribute("horariosAgenda", horariosAgenda);

        return new ModelAndView("/agenda/list", model);
    }

    @GetMapping("/gerar/medico/{id}")
    public ModelAndView gerar(
            @RequestParam(value = "data", required = false) String data,
            @PathVariable("id") Long id,
            ModelMap model
    ){
        LocalDate dataFormat = processarData(data);
        AgendaGerar agendaGerarPadrao = agendaGerarRepository.consultaPorMedico(id);

        model.addAttribute("agendaGerar", Objects.requireNonNullElseGet(agendaGerarPadrao, AgendaGerar::new));
        model.addAttribute("medico", medicoRepository.medico(id));
        model.addAttribute("data", dataFormat);
        model.addAttribute("horarios", new ArrayList<>());
        return new ModelAndView("/agenda/generate", model);
    }

    public LocalDate processarData(String data) {
        LocalDate dataFormat;

        if (data == null || data.trim().isEmpty()) {
            dataFormat = LocalDate.now();
        } else {
            try {
                dataFormat = LocalDate.parse(data);
            } catch (DateTimeParseException e) {
                dataFormat = LocalDate.now();
            }
        }
        return dataFormat;
    }

    @PostMapping("/gerar/medico/{medicoId}")
    public ModelAndView doGerar(
            @Valid AgendaGerar agendaGerar,
            BindingResult result,
            ModelMap model,
            RedirectAttributes redirectAttributes
    ) {
        //TODO precisariamos de mais algumas validações aqui, ver com o professor
        // Como o inicio ser menor que o fim em todos os campos
        Medico medico = medicoRepository.medico(agendaGerar.getMedicoId());

        model.addAttribute("medico", medico);
        model.addAttribute("data", agendaGerar.getData());
        model.addAttribute("agendaGerar", agendaGerar);

        if(!result.hasErrors()){
            if (agendaGerar.getIntervalos() != null) {
                for (IntervaloAgendaGerar intervalo : agendaGerar.getIntervalos()) {
                    intervalo.setAgendaGerar(agendaGerar);
                }
            }

            TipoAgendaGerarEnum tipo = TipoAgendaGerarEnum.valueOf(agendaGerar.getTipo());

            if(tipo.equals(TipoAgendaGerarEnum.SALVAR_PADRAO)){
                AgendaGerar agendaGerarPadrao = agendaGerarRepository.consultaPorMedico(agendaGerar.getMedicoId());
                if(agendaGerarPadrao != null){
                    agendaGerarRepository.remove(agendaGerarPadrao.getId());
                }
                agendaGerarRepository.save(agendaGerar);
                model.addAttribute("mensagem", "Padrão de geração da agenda salvo com sucesso!");
            } else if (tipo.equals(TipoAgendaGerarEnum.SALVAR)){
                List<HorarioAgenda> horariosAgenda = this.gerarHorarios(agendaGerar, medico);

                List<HorarioAgenda> horariosDisponiveis = horariosAgenda.stream()
                        .filter(horario -> horario.getStatusHorarioAgenda() != null &&
                                horario.getStatusHorarioAgenda() == StatusHorarioAgendaEnum.DISPONIVEL)
                        .collect(Collectors.toList());

                horarioAgendaRepository.saveAll(horariosDisponiveis);
                redirectAttributes.addFlashAttribute("mensagem", "Agenda gerada com sucesso!");

                DateTimeFormatter formatador = DateTimeFormatter.ISO_LOCAL_DATE;
                String dataFormatada = agendaGerar.getData().format(formatador);

                return new ModelAndView("redirect:/agenda/listar?data=" + dataFormatada + "&medico=" + medico.getId());
            }

            model.addAttribute("horarios", this.gerarHorarios(agendaGerar, medico));
        }

        return new ModelAndView("/agenda/generate", model);
    }

    public List<HorarioAgenda> gerarHorarios(AgendaGerar agendaGerar, Medico medico) {
        LocalTime inicio = agendaGerar.getInicio();
        LocalTime fim = agendaGerar.getFim();
        LocalDate data = agendaGerar.getData();
        Integer tempoConsulta = agendaGerar.getTempo();
        List<IntervaloAgendaGerar> intervalos = agendaGerar.getIntervalos();

        List<HorarioAgenda> horariosExistentes = horarioAgendaRepository.search(medico.getId(), data, StatusHorarioAgendaEnum.DISPONIVEL);
        List<HorarioAgenda> horarios = new ArrayList<>();

        while (inicio.plusMinutes(tempoConsulta).isBefore(fim) || inicio.plusMinutes(tempoConsulta).equals(fim)) {
            LocalTime inicioConsulta = inicio;
            LocalTime fimConsulta = inicio.plusMinutes(tempoConsulta);

            boolean conflitaComIntervalo = false;
            for (IntervaloAgendaGerar intervalo : intervalos) {
                if (inicioConsulta.isBefore(intervalo.getFim()) && fimConsulta.isAfter(intervalo.getInicio())) {
                    conflitaComIntervalo = true;
                    break;
                }
            }

            if (!conflitaComIntervalo) {
                boolean conflitaComAgendamentoExistente = false;
                for (HorarioAgenda horarioExistente : horariosExistentes) {
                    if (inicioConsulta.isBefore(horarioExistente.getFim()) && fimConsulta.isAfter(horarioExistente.getInicio())) {
                        conflitaComAgendamentoExistente = true;
                        break;
                    }
                }

                HorarioAgenda novoHorario = new HorarioAgenda();
                novoHorario.setInicio(inicioConsulta);
                novoHorario.setFim(fimConsulta);
                novoHorario.setMedico(medico);
                novoHorario.setData(data);
                if (conflitaComAgendamentoExistente) {
                    novoHorario.setStatusHorarioAgenda(StatusHorarioAgendaEnum.CONFLITO);
                } else {
                    novoHorario.setStatusHorarioAgenda(StatusHorarioAgendaEnum.DISPONIVEL);
                }
                horarios.add(novoHorario);
            }

            inicio = fimConsulta;
        }

        return horarios;
    }

    @GetMapping("/cancelar/{id}")
    public ModelAndView doDeletar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        HorarioAgenda horarioAgenda = horarioAgendaRepository.find(id);

        if (horarioAgenda == null) {
            redirectAttributes.addFlashAttribute("erro", "Horário não encontrado!");
            return new ModelAndView("redirect:/agenda/listar");
        }

        if (horarioAgenda.getStatusHorarioAgenda() ==  StatusHorarioAgendaEnum.CANCELADO) {
            redirectAttributes.addFlashAttribute("erro", "O horário já esta cancelado!");
            return new ModelAndView("redirect:/agenda/listar");
        }

        horarioAgenda.setStatusHorarioAgenda(StatusHorarioAgendaEnum.CANCELADO);
        horarioAgenda.setConsulta(null);
        horarioAgendaRepository.update(horarioAgenda);
        redirectAttributes.addFlashAttribute("mensagem", "Horário cancelado com sucesso!");
        return new ModelAndView("redirect:/agenda/listar");
    }

    @GetMapping("/marcar/{id}")
    public ModelAndView marcar(
            @PathVariable("id") Long id,
            ModelMap model,
            Consulta consulta,
            RedirectAttributes redirectAttributes
    ){
        HorarioAgenda horarioAgenda = horarioAgendaRepository.find(id);

        if (horarioAgenda == null) {
            redirectAttributes.addFlashAttribute("erro", "Horário não encontrado!");
            return new ModelAndView("redirect:/agenda/listar");
        }

        consulta.setMedico(horarioAgenda.getMedico());
        consulta.setData(horarioAgenda.getData().atTime(horarioAgenda.getInicio()));

        model.addAttribute("pacientes", pacienteRepository.pacientes());
        model.addAttribute("medicos", medicoRepository.medicos());
        model.addAttribute("consulta", consulta);
        return new ModelAndView("/agenda/form", model);
    }

    @PostMapping("/marcar/{horarioId}")
    public ModelAndView doMarcar(
            @PathVariable("horarioId") Long id,
            Consulta consulta,
            RedirectAttributes redirectAttributes
    ){
        HorarioAgenda horarioAgenda = horarioAgendaRepository.find(id);

        if (horarioAgenda == null) {
            redirectAttributes.addFlashAttribute("erro", "Horário não encontrado!");
            return new ModelAndView("redirect:/agenda/listar");
        }

        consulta.setMedico(horarioAgenda.getMedico());

        var paciente = pacienteRepository.paciente(consulta.getPaciente().getId());

        if (paciente == null) {
            redirectAttributes.addFlashAttribute("erro", "Paciente não encontrado!");
            return new ModelAndView("redirect:/agenda/listar");
        }

        consulta.setPaciente(paciente);
        consulta.setData(horarioAgenda.getData().atTime(horarioAgenda.getInicio()));
        consultaRepository.save(consulta);
        horarioAgenda.setConsulta(consulta);
        horarioAgenda.setStatusHorarioAgenda(StatusHorarioAgendaEnum.AGENDADO);
        horarioAgendaRepository.update(horarioAgenda);

        redirectAttributes.addFlashAttribute("mensagem", "Consulta marcada com sucesso!");
        return new ModelAndView("redirect:/agenda/listar");
    }
}