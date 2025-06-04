package com.tecsup.demo.controllers;

import com.tecsup.demo.domain.entities.Alumno;
import com.tecsup.demo.services.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import java.util.Map;

@Controller
@SessionAttributes("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/listarAlumno")
    public String listarAlumno(Model model) {
        model.addAttribute("alumnos", alumnoService.listar());
        return "listarAlumno";
    }

    @GetMapping("/formAlumno")
    public String crear(Map<String, Object> model) {
        model.put("alumno", new Alumno());
        return "formAlumno";
    }

    @PostMapping("/formAlumno")
    public String guardar(@Valid Alumno alumno, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "formAlumno";
        }
        alumnoService.grabar(alumno);
        status.setComplete();
        return "redirect:/listarAlumno";
    }

    @GetMapping("/formAlumno/{codigo}")
    public String editar(@PathVariable String codigo, Map<String, Object> model) {
        Alumno alumno = alumnoService.buscar(codigo);
        if (alumno == null) {
            return "redirect:/listarAlumno";
        }
        model.put("alumno", alumno);
        return "formAlumno";
    }

    @GetMapping("/eliminarAlumno/{codigo}")
    public String eliminar(@PathVariable String codigo) {
        alumnoService.eliminar(codigo);
        return "redirect:/listarAlumno";
    }

    @RequestMapping("/verAlumno")
    public String verAlumno(Model model) {
        model.addAttribute("alumnos", alumnoService.listar());
        return "alumno/ver";
    }
}
