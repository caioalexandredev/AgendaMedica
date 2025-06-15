package br.edu.ifto.sistemaconsulta.controller;

import br.edu.ifto.sistemaconsulta.dto.AgendaGerarDTO;
import br.edu.ifto.sistemaconsulta.model.entity.Medico;
import br.edu.ifto.sistemaconsulta.model.repository.MedicoRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@Controller
@RequestMapping("agenda")
public class AgendaController {
    @Autowired
    MedicoRepository medicoRepository;

    @GetMapping("/listar")
    public ModelAndView listar(
            @RequestParam(value = "medico", required = false) String medico,
            @RequestParam(value = "data", required = false) String data,
            ModelMap model) {

        LocalDate dataFormat;

        if (data == null || data.isEmpty()) {
            dataFormat = LocalDate.now();
        } else {
            dataFormat = LocalDate.parse(data);
        }

        model.addAttribute("data", dataFormat);
        model.addAttribute("medico", medico);
        model.addAttribute("medicos", medicoRepository.medicos());
        return new ModelAndView("/agenda/list", model);
    }

    @GetMapping("/gerar/medico/{id}")
    public ModelAndView gerar(
            @RequestParam(value = "data", required = false) String data,
            @PathVariable("id") Long id,
            ModelMap model
    ){
        LocalDate dataFormat;

        //TODO Validar melhor essa data, se for inválida carregar dia atual
        if (data == null || data.isEmpty()) {
            dataFormat = LocalDate.now();
        } else {
            dataFormat = LocalDate.parse(data);
        }

        model.addAttribute("config", new AgendaGerarDTO());
        model.addAttribute("medico", medicoRepository.medico(id));
        model.addAttribute("data", dataFormat);
        return new ModelAndView("/agenda/generate", model);
    }

    @PostMapping("/gerar")
    public ModelAndView doGerar(
            @Valid @ModelAttribute AgendaGerarDTO agendaGerarDTO,
            ModelMap model,
            BindingResult result
    ) {
        model.addAttribute("medico", medicoRepository.medico(agendaGerarDTO.getMedicoId()));
        model.addAttribute("data", agendaGerarDTO.getData());
        model.addAttribute("config", agendaGerarDTO);
        return new ModelAndView("/agenda/generate", model);
    }
}