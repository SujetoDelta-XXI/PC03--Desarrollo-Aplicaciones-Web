package com.tecsup.demo.controllers;

import com.tecsup.demo.domain.entities.Curso;
import com.tecsup.demo.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import java.util.Map;

@Controller
@SessionAttributes("curso")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @RequestMapping(value = "/listarCurso", method = RequestMethod.GET)
    public String listarCurso(Model model) {
        model.addAttribute("cursos", cursoService.listar());
        return "listarCurso";
    }

    @RequestMapping(value = "/formCurso")
    public String crear(Map<String, Object> model) {

        Curso curso = new Curso();
        model.put("curso", curso);
        return "formCurso";
    }

    @RequestMapping(value = "/formCurso", method = RequestMethod.POST)
    public String guardar(@Valid Curso curso, BindingResult result, Model model, SessionStatus status) {
        if(result.hasErrors()) {
            return "formCurso";
        }

        cursoService.grabar(curso);
        status.setComplete();
        return "redirect:listarCurso";
    }

    //Form para editar
    @RequestMapping(value="/formCurso/{id}")
    public String editar(@PathVariable(value="id") Integer id, Map<String, Object> model) {

        Curso curso = null;

        if(id > 0) {
            curso = cursoService.buscar(id);
        } else {
            return "redirect:/listarCurso";
        }
        model.put("curso", curso);
        return "formCurso";
    }

    //Eliminar
    @RequestMapping(value="/eliminarCurso/{id}")
    public String eliminar(@PathVariable(value="id") Integer id) {

        if(id > 0) {
            cursoService.eliminar(id);
        }
        return "redirect:/listarCurso";
    }

    //Ver
    @RequestMapping(value="/verCurso")
    public String ver(Model model) {
        model.addAttribute("cursos", cursoService.listar());
        model.addAttribute("titulo", "Lista de cursos");

        return "curso/ver";
    }
}











