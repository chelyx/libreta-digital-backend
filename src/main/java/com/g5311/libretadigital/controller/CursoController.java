package com.g5311.libretadigital.controller;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

import com.g5311.libretadigital.model.dto.CursoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.service.CursoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @GetMapping("/profesor/{auth0Id}")
    public List<Curso> obtenerCursosPorProfesor(@PathVariable String auth0Id) {
        return cursoService.obtenerCursosPorDocente(auth0Id);
    }

    @GetMapping("/mios")
    public List<Curso> obtenerMisCursos(@AuthenticationPrincipal Jwt jwt) {
        String auth0Id = jwt.getSubject(); // viene como "auth0|xxxx"
        return cursoService.obtenerCursosPorDocente(auth0Id);
    }

    // PROFESOR o BEDEL
    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Curso> obtenerPorCodigo(
            @PathVariable String codigo) {
        return ResponseEntity.ok(cursoService.obtenerPorCodigo(codigo));
    }

    // (A) POST usando el docente del JWT
    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/mios")
    public ResponseEntity<Curso> crearMiCurso(@AuthenticationPrincipal Jwt jwt,
                                              @RequestBody CursoDto dto) throws ParseException {
        String docenteAuth0Id = jwt.getSubject();
        Curso creado = cursoService.crearCurso(dto, docenteAuth0Id);
        // 201 + Location (si tenés getId en Curso)
        return ResponseEntity
                .created(URI.create("/api/cursos/" + creado.getId()))
                .body(creado);
    }

    //POST indicando el docente en el body
    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping
    public ResponseEntity<Curso> crearCurso(@RequestBody CursoDto dto) throws ParseException {
        Curso creado = cursoService.crearCurso(dto, null);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/cursos/" + creado.getId()))
                .body(creado);
    }

}
