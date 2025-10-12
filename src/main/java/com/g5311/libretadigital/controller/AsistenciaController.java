package com.g5311.libretadigital.controller;

import com.g5311.libretadigital.model.AlumnoAula;
import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.AsistenciaDetalleView;
import com.g5311.libretadigital.repository.AlumnoAulaRepository;

import com.g5311.libretadigital.repository.AsistenciaRepository;

import com.g5311.libretadigital.service.AsistenciaService;
import com.g5311.libretadigital.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    @Autowired
    private AlumnoAulaRepository alumnoAulaRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
    }

    // Este endpoint espera una lista de IDs de relaciones AlumnoAula
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarAsistencia(@RequestBody Map<String, Object> body) {
        String fechaStr = (String) body.get("fecha");

        @SuppressWarnings("unchecked")
        Map<String, Boolean> asistencias = (Map<String, Boolean>) body.get("asistencias");

        for (Map.Entry<String, Boolean> entry : asistencias.entrySet()) {
            UUID alumnoAulaId = UUID.fromString(entry.getKey());
            boolean presente = entry.getValue();

            AlumnoAula alumnoAula = alumnoAulaRepository.findById(alumnoAulaId)
                    .orElseThrow(() -> new RuntimeException("AlumnoAula no encontrado: " + alumnoAulaId));

            Asistencia asistencia = new Asistencia();
            asistencia.setFecha(fechaStr);
            asistencia.setEstado(presente ? "Presente" : "Ausente");
            asistenciaRepository.save(asistencia);

            alumnoAula.getAsistencias().add(asistencia);
            alumnoAulaRepository.save(alumnoAula);
        }

        return ResponseEntity.ok("Asistencias registradas con fecha " + fechaStr);

    }
    // Detalle de las asistencias por id de alumno
    @GetMapping("/alumno/{alumnoId}/detalle")
    public ResponseEntity<List<AsistenciaDetalleView>> obtenerDetalle(
            @PathVariable UUID alumnoId
    ) {
        List<AsistenciaDetalleView> data = asistenciaService.obtenerDetallePorAlumno(alumnoId);
        return ResponseEntity.ok(data);
    }
    }
