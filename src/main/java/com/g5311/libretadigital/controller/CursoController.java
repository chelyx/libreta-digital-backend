package com.g5311.libretadigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.service.CursoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PreAuthorize("hasRole('PROFESOR')")
    @GetMapping("/profesor/{auth0Id}")
    public List<Curso> obtenerCursosPorProfesor(@PathVariable String auth0Id) {
        return cursoService.obtenerCursosPorDocente(auth0Id);
    }

    @GetMapping("/mios")
    public List<Curso> obtenerMisCursos(@AuthenticationPrincipal Jwt jwt) {
        String auth0Id = jwt.getSubject(); // viene como "auth0|xxxx"
        return cursoService.obtenerCursosPorDocente(auth0Id);
    }
}
