package com.g5311.libretadigital.controller;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.NotaBulkDto;
import com.g5311.libretadigital.model.dto.NotaResponse;
import com.g5311.libretadigital.service.NotaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    // Cargar una nota
    @PostMapping("/curso/{cursoId}")
    public Nota guardarNota(
            @PathVariable UUID cursoId,
            @RequestBody Map<String, Object> body) {
        String alumnoAuth0Id = (String) body.get("alumnoAuth0Id");
        String descripcion = (String) body.get("descripcion");
        Double valor = ((Number) body.get("valor")).doubleValue();

        return notaService.guardarNota(cursoId, alumnoAuth0Id, descripcion, valor);
    }

    // Obtener todas las notas de un curso
    @GetMapping("/curso/{cursoId}")
    public List<NotaResponse> obtenerNotasDeCurso(@PathVariable UUID cursoId) {
        return notaService.obtenerNotasDeCurso(cursoId);
    }

    // Obtener las notas de un alumno en un curso
    // El auth0Id normalmente viene con un pipe (auth0|xxxxx), pero en el path hay
    // que replazar el '|' por '%7C' para que funcione
    @GetMapping("/curso/{cursoId}/alumno/{auth0Id}")
    public List<Nota> obtenerNotasDeAlumno(@PathVariable UUID cursoId, @PathVariable String auth0Id) {
        return notaService.obtenerNotasDeAlumnoEnCurso(cursoId, auth0Id);
    }

    // Obtener las notas de un alumno en TODOS sus cursos
    @GetMapping("/alumno/{auth0Id}")
    public ResponseEntity<List<Nota>> getNotasPorAlumno(@PathVariable String auth0Id) {
        List<Nota> notas = notaService.obtenerNotasPorAlumno(auth0Id);
        return ResponseEntity.ok(notas);
    }

    @PostMapping("/curso/{cursoId}/bulk")
    public List<Nota> guardarNotasEnBulk(
            @PathVariable UUID cursoId,
            @RequestBody List<Map<String, Object>> notasData) {
        List<Nota> notas = notasData.stream().map(data -> {
            Nota n = new Nota();
            n.setAlumnoAuth0Id((String) data.get("alumnoId"));
            n.setDescripcion((String) data.get("descripcion"));
            n.setValor(((Number) data.get("valor")).doubleValue());
            return n;
        }).toList();

        return notaService.guardarNotasEnBulk(cursoId, notas);
    }

    @PostMapping("/actualizar/{notaId}")
    public Nota updateNota(
            @PathVariable UUID notaId,
            @RequestBody Map<String, Object> body) {
        Double valor = ((Number) body.get("valor")).doubleValue();

        return notaService.updateNota(notaId, valor);
    }

    @PostMapping("/actualizar-bulk")
    public List<Nota> updateNotasBulk(
            @RequestBody NotaBulkDto notasData) {

        return notaService.updateNotasBulk(notasData);
    }

}
