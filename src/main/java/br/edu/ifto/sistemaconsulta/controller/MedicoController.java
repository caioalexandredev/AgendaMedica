package br.edu.ifto.sistemaconsulta.controller;

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

import java.util.List;

@Transactional
@Controller
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    MedicoRepository repository;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(Medico medico){
        return new ModelAndView("/medico/form");
    }

    @PostMapping("/cadastrar")
    public ModelAndView doCadastrar(@Valid Medico medico, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            return cadastrar(medico);

        repository.save(medico);
        redirectAttributes.addFlashAttribute("mensagem", "Médico cadastrado com sucesso!");
        return new ModelAndView("redirect:/medicos/listar");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("medico", repository.medico(id));
        return new ModelAndView("/medico/form", model);
    }

    @PostMapping("/editar")
    public ModelAndView doEditar(
            @Valid Medico medico,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            ModelMap model
    ){
        if(result.hasErrors()){
            model.addAttribute("medico", medico);
            return new ModelAndView("/medico/form", model);
        }

        repository.update(medico);
        redirectAttributes.addFlashAttribute("mensagem", "Médico editado com sucesso!");
        return new ModelAndView("redirect:/medicos/listar");
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView doDeletar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        Medico medico = repository.medico(id);
        if (medico.getConsultas() != null && !medico.getConsultas().isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Este médico possui consultas e não pode ser removido.");
            return new ModelAndView("redirect:/medicos/listar");
        }

        repository.remove(id);
        redirectAttributes.addFlashAttribute("mensagem", "Médico removido com sucesso!");
        return new ModelAndView("redirect:/medicos/listar");
    }

    @GetMapping("/listar")
    public ModelAndView listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "crm", required = false) String crm,
            ModelMap model) {
        List<Medico> medicos = repository.search(nome, crm);
        model.addAttribute("medicos", medicos);
        model.addAttribute("nome", nome);
        model.addAttribute("crm", crm);
        return new ModelAndView("/medico/list", model);
    }
}