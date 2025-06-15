package br.edu.ifto.sistemaconsulta.controller;

import br.edu.ifto.sistemaconsulta.model.entity.Medico;
import br.edu.ifto.sistemaconsulta.model.entity.Paciente;
import br.edu.ifto.sistemaconsulta.model.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Transactional
@Controller
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    PacienteRepository repository;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(Paciente paciente){ return new ModelAndView("/paciente/form"); }

    @PostMapping("/cadastrar")
    public ModelAndView doCadastrar(@Valid Paciente paciente, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            return cadastrar(paciente);

        repository.save(paciente);
        redirectAttributes.addFlashAttribute("mensagem", "Paciente cadastrado com sucesso!");
        return new ModelAndView("redirect:/pacientes/listar");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("paciente", repository.paciente(id));
        return new ModelAndView("/paciente/form", model);
    }

    @PostMapping("/editar")
    public ModelAndView doEditar(
            @Valid Paciente paciente,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            ModelMap model
    ){
        if(result.hasErrors()){
            model.addAttribute("paciente", paciente);
            return new ModelAndView("/paciente/form", model);
        }
        repository.update(paciente);
        redirectAttributes.addFlashAttribute("mensagem", "Paciente editado com sucesso!");
        return new ModelAndView("redirect:/pacientes/listar");
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView doDeletar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Paciente paciente = repository.paciente(id);

        if (paciente.getConsultas() != null && !paciente.getConsultas().isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Este paciente possui consultas e não pode ser removido.");
            return new ModelAndView("redirect:/pacientes/listar");
        }

        repository.remove(id);
        redirectAttributes.addFlashAttribute("mensagem", "Paciente removido com sucesso!");
        return new ModelAndView("redirect:/pacientes/listar");
    }

    @GetMapping("/listar")
    public ModelAndView listar(@RequestParam(value = "nome", required = false) String nome, ModelMap model){
        List<Medico> pacientes = repository.search(nome);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("nome", nome);
        return new ModelAndView("/paciente/list", model);
    }
}