package br.edu.ifto.sistemaconsulta.controller;

import br.edu.ifto.sistemaconsulta.model.entity.Consulta;
import br.edu.ifto.sistemaconsulta.model.entity.HorarioAgenda;
import br.edu.ifto.sistemaconsulta.model.entity.Medico;
import br.edu.ifto.sistemaconsulta.model.repository.ConsultaRepository;
import br.edu.ifto.sistemaconsulta.model.repository.HorarioAgendaRepository;
import br.edu.ifto.sistemaconsulta.model.repository.MedicoRepository;
import br.edu.ifto.sistemaconsulta.model.repository.PacienteRepository;
import br.edu.ifto.sistemaconsulta.model.validation.Insert;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Transactional
@Controller
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    ConsultaRepository repository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    private HorarioAgendaRepository horarioAgendaRepository;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(ModelMap model, Consulta consulta){
        model.addAttribute("pacientes", pacienteRepository.pacientes());
        model.addAttribute("medicos", medicoRepository.medicos());
        model.addAttribute("consulta", consulta);
        return new ModelAndView("/consulta/form", model);
    }

    @PostMapping("/cadastrar")
    public ModelAndView doCadastrar(
            @Validated(Insert.class) Consulta consulta,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            ModelMap model
    ){
        if(result.hasErrors())
            return cadastrar(model, consulta);

        var medico = medicoRepository.medico(consulta.getMedico().getId());
        var paciente = pacienteRepository.paciente(consulta.getPaciente().getId());

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        repository.save(consulta);
        redirectAttributes.addFlashAttribute("mensagem", "Consulta cadastrada com sucesso!");
        return new ModelAndView("redirect:/consultas/listar");
    }

    @GetMapping("/listar")
    public ModelAndView listar(
            @RequestParam(value = "datainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datainicio,
            @RequestParam(value = "datafim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datafim,
            ModelMap model) {

        model.addAttribute("consultas", repository.search(datainicio, datafim));
        model.addAttribute("datainicio", datainicio);
        model.addAttribute("datafim", datafim);
        return new ModelAndView("/consulta/list", model);
    }

    @GetMapping("/medico/{id}/listar")
    public ModelAndView listarPorMedicos(ModelMap model, @PathVariable("id") Long id){
        var medico = medicoRepository.medico(id);

        model.addAttribute("consultas", repository.consultasPorMedico(id));
        model.addAttribute("subtitulo", "Médico: " + medico.getNome());
        return new ModelAndView("/consulta/list", model);
    }

    @GetMapping("/paciente/{id}/listar")
    public ModelAndView listarPorPaciente(ModelMap model, @PathVariable("id") Long id){
        var paciente = pacienteRepository.paciente(id);

        model.addAttribute("consultas", repository.consultasPorPaciente(id));
        model.addAttribute("subtitulo", "Paciente: " + paciente.getNome());
        return new ModelAndView("/consulta/list", model);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView doDeletar(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        Consulta consulta = repository.consulta(id);

        if (consulta == null) {
            redirectAttributes.addFlashAttribute("erro", "Consulta não encontrada!");
            return new ModelAndView("redirect:/agenda/listar");
        }

        if (consulta.getHorariosAgenda() != null && !consulta.getHorariosAgenda().isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Esta consulta possui um agendamento vinculado.");
            return new ModelAndView("redirect:/consultas/listar");
        }

        repository.remove(id);
        redirectAttributes.addFlashAttribute("mensagem", "Consulta removida com sucesso!");
        return new ModelAndView("redirect:/consultas/listar");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") int id, ModelMap model){
        model.addAttribute("pacientes", pacienteRepository.pacientes());
        model.addAttribute("medicos", medicoRepository.medicos());
        model.addAttribute("consulta", repository.consulta(id));
        return new ModelAndView("/consulta/form", model);
    }

    @PostMapping("/editar")
    public ModelAndView doEditar(
            @Validated(Insert.class) Consulta consulta,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            ModelMap model
    ){
        if(result.hasErrors()){
            model.addAttribute("pacientes", pacienteRepository.pacientes());
            model.addAttribute("medicos", medicoRepository.medicos());
            model.addAttribute("consulta", consulta);
            return new ModelAndView("/consulta/form", model);
        }

        //TODO se a consulta tiver vinculado a um horário,não permitir troca de data e médico

        var medico = medicoRepository.medico(consulta.getMedico().getId());
        var paciente = pacienteRepository.paciente(consulta.getPaciente().getId());

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        repository.update(consulta);
        redirectAttributes.addFlashAttribute("mensagem", "Consulta editada com sucesso!");
        return new ModelAndView("redirect:/consultas/listar");
    }
}