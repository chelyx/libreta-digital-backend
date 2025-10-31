package com.g5311.libretadigital.controller;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.dto.AsistenciaAlumnoDto;
import com.g5311.libretadigital.model.dto.AsistenciaBulkRequest;
import com.g5311.libretadigital.model.dto.AsistenciaResponse;
import com.g5311.libretadigital.service.AsistenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping
    public Asistencia registrar(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam UUID cursoId,
            @RequestParam Boolean presente) {
        String alumnoId = jwt.getSubject(); // auth0_id
        LocalDate fechaHoy = LocalDate.now();

        return asistenciaService.registrarAsistencia(cursoId, alumnoId, fechaHoy, presente);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/guardar")
    public ResponseEntity<?> registrarAsistenciasCurso(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody AsistenciaBulkRequest request) {
        // TODO: validar que jwt.getSubject() == profesor del curso

        asistenciaService.registrarAsistenciasMasivas(request.getCursoId(), request.getAsistencias());

        return ResponseEntity.ok(Map.of("status", "Asistencias registradas correctamente"));
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @GetMapping("/{cursoId}")
    public ResponseEntity<?> obtenerAsistencias(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID cursoId) {
        List<AsistenciaResponse> asistencias = asistenciaService.obtenerAsistenciasPorCurso(cursoId);

        return ResponseEntity.ok(asistencias);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PutMapping("/{cursoId}/alumno/actualizar")
    public ResponseEntity<?> actualizarAsistencia(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID cursoId,
            @RequestBody AsistenciaAlumnoDto asistencia) {

        Asistencia actualizada = asistenciaService.actualizarAsistencia(cursoId, asistencia.getAlumnoId(), asistencia.getFecha(), asistencia.isPresente());
        return ResponseEntity.ok(actualizada);
    }

    // === ACTUALIZAR VARIAS ASISTENCIAS (bulk) ===
    // Reutilizamos AsistenciaBulkRequest { UUID cursoId; List<AsistenciaAlumnoDto> asistencias; }
    @PreAuthorize("hasRole('PROFESOR')")
    @PutMapping("actualizar/bulk")
    public ResponseEntity<?> actualizarAsistenciasMasivas(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody AsistenciaBulkRequest request) {

        List<Asistencia> result = asistenciaService.actualizarAsistenciasMasivas(
                request.getCursoId(),
                request.getAsistencias()
        );
        return ResponseEntity.ok(Map.of(
                "updated", result.size(),
                "items", result
        ));
    }

}