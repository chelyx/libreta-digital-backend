package com.g5311.libretadigital.controller;

import org.springframework.web.bind.annotation.*;

import com.g5311.libretadigital.model.Alumno;
import com.g5311.libretadigital.model.dto.AlumnoDto;
import com.g5311.libretadigital.model.dto.AlumnoDto;
import com.g5311.libretadigital.service.AlumnoService;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public List<Alumno> getAllAlumnos() {
        return alumnoService.getAllAlumnos();
    }

    @PostMapping
    public Alumno createAlumno(@RequestBody AlumnoDto alumno) {
        return alumnoService.saveAlumno(alumno);
    }
}
