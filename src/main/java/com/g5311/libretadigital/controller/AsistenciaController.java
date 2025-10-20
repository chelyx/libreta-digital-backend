package com.g5311.libretadigital.controller;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.dto.AsistenciaBulkRequest;
import com.g5311.libretadigital.service.AsistenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping
    public Asistencia registrar(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam Long cursoId,
            @RequestParam Boolean presente) {
        String alumnoId = jwt.getSubject(); // auth0_id
        LocalDate fechaHoy = LocalDate.now();

        return asistenciaService.registrarAsistencia(cursoId, alumnoId, fechaHoy, presente);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/curso")
    public String registrarAsistenciasCurso(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody AsistenciaBulkRequest request) {
        // TODO: validar que jwt.getSubject() == profesor del curso
        List<Asistencia> asistencias = request.getAsistencias().stream().map(dto -> {
            Asistencia a = new Asistencia();
            a.setAlumnoId(dto.getAlumnoId());
            a.setPresente(dto.isPresente());
            return a;
        }).toList();

        asistenciaService.registrarAsistenciasMasivas(request.getCursoId(), request.getFecha(), asistencias);

        return "Asistencias registradas correctamente";
    }

}