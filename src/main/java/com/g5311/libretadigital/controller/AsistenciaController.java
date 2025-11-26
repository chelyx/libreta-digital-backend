package com.g5311.libretadigital.controller;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.model.dto.AsistenciaAlumnoDto;
import com.g5311.libretadigital.model.dto.AsistenciaBulkRequest;
import com.g5311.libretadigital.model.dto.AsistenciaResponse;
import com.g5311.libretadigital.model.dto.HistorialAsistenciaDto;
import com.g5311.libretadigital.service.AsistenciaService;
import com.g5311.libretadigital.service.CursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        String alumnoId = jwt.getSubject(); // auth0_i
        // ZoneId zoneUTC3 = ZoneId.of("America/Argentina/Buenos_Aires");
        // LocalDate fechaHoy = LocalDate.now(zoneUTC3);
        LocalDate fechaHoy = LocalDate.now();
        return asistenciaService.registrarAsistencia(cursoId, alumnoId, fechaHoy, presente);
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @PostMapping("/guardar")
    public List<String> registrarAsistenciasCurso(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody AsistenciaBulkRequest request) {

        return asistenciaService.registrarAsistenciasMasivas(request.getCursoId(), request.getAsistencias(),
                jwt.getSubject());

        // return ResponseEntity.ok(Map.of("status", "Asistencias registradas
        // correctamente"));
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @GetMapping("/{cursoId}")
    public ResponseEntity<?> obtenerAsistencias(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID cursoId) {
        List<AsistenciaResponse> asistencias = asistenciaService.obtenerAsistenciasPorCurso(cursoId);

        return ResponseEntity.ok(asistencias);
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @GetMapping("/historial/{cursoId}")
    public HistorialAsistenciaDto getHistorial(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID cursoId) {

        List<AsistenciaResponse> asistencias = asistenciaService.obtenerAsistenciasPorCurso(cursoId);

        HistorialAsistenciaDto historial = asistenciaService.agruparAsistencias(asistencias);

        return historial;
    }

    // obtener las asistencias del alumno logueado en TODOS sus cursos
    @GetMapping("/me")
    public List<AsistenciaResponse> obtenerMisAsistencias(@AuthenticationPrincipal Jwt jwt
    // , @RequestParam("alumnoID") String auth0Id
    ) {
        String auth0Id = jwt.getSubject(); // "auth0|xxxx"
        return asistenciaService.obtenerAsistenciasPorAlumno(auth0Id);
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @PutMapping("/{cursoId}/alumno/actualizar")
    public ResponseEntity<?> actualizarAsistencia(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID cursoId,
            @RequestBody AsistenciaAlumnoDto asistencia) {

        Asistencia actualizada = asistenciaService.actualizarAsistencia(cursoId, asistencia.getAlumnoId(),
                asistencia.getFecha(), asistencia.isPresente());
        return ResponseEntity.ok(actualizada);
    }

    // === ACTUALIZAR VARIAS ASISTENCIAS (bulk) ===
    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @PutMapping("/actualizar/bulk")
    public ResponseEntity<?> actualizarAsistenciasMasivas(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody AsistenciaBulkRequest request) {

        List<Asistencia> result = asistenciaService.actualizarAsistenciasMasivas(
                request.getCursoId(),
                request.getAsistencias());
        return ResponseEntity.ok(Map.of(
                "updated", result.size(),
                "items", result));
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @PostMapping("/{cursoId}/guardar-historial")
    public ResponseEntity<?> guardar( @AuthenticationPrincipal Jwt jwt,@RequestBody HistorialAsistenciaDto historial, @PathVariable UUID cursoId) {
        asistenciaService.guardarHistorial(historial, cursoId);
        return ResponseEntity.ok().body("{\"mensaje\":\"Asistencias guardadas\"}");
    }

}