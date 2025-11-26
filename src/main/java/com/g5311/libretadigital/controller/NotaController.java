package com.g5311.libretadigital.controller;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.NotaBulkDto;
import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.model.dto.NotaResponse;
import com.g5311.libretadigital.service.BfaTsaService;
import com.g5311.libretadigital.service.NotaService;
import com.g5311.libretadigital.service.TsaService2;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private TsaService2 tsaService;

    @Autowired
    private BfaTsaService bfaTsaService;

    @Autowired
    private TaskScheduler scheduler;

    // Cargar una nota
    @PostMapping("/curso/{cursoId}")
    public Nota guardarNota(
            @AuthenticationPrincipal Jwt jwt,
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

    // Obtener las notas de un alumno en TODOS sus cursos
    @GetMapping("/me")
    public List<NotaResponse> getNotasPorAlumno(@AuthenticationPrincipal Jwt jwt) {
        String auth0Id = jwt.getSubject();
        return notaService.obtenerNotasPorAlumno(auth0Id);
    }

    @PostMapping("/curso/{cursoId}/bulk")
    public List<Nota> guardarNotasEnBulk(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID cursoId,
            @RequestBody List<NotaDto> notasData) {

        List<Nota> notasGuardadas = notaService.guardarNotasEnBulk(cursoId, notasData);
        scheduler.schedule(
                () -> notaService.enviarNotasPorMailCurso(notasGuardadas),
                Instant.now().plus(Duration.ofMinutes(1)));

        return notasGuardadas;
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @PostMapping("/actualizar/{notaId}")
    public Nota updateNota(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID notaId,
            @RequestBody Map<String, Object> body) {
        Double valor = ((Number) body.get("valor")).doubleValue();

        return notaService.updateNota(notaId, valor);
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @PostMapping("/actualizar-bulk")
    public List<Nota> updateNotasBulk(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody NotaBulkDto notasData) {

        return notaService.updateNotasBulk(notasData);
    }


    @PostMapping("/sellar-this")
    public ResponseEntity<Map<String, String>> sellarListaDeNotas(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody List<UUID> notasData) {

       try {
            List<Nota> notas = notaService.findAllById(notasData);
            int cantidad = tsaService.generarNotaRequest(notas);
            System.out.println("Notas registradas para TSA: " + cantidad);
            int cantidad2 = bfaTsaService.primerSelloABFA();
            System.out.println("Notas selladas en BFA: " + cantidad2);

            scheduler.schedule(
                    () -> bfaTsaService.generarRecibosDefinitivos(),
                    Instant.now().plus(Duration.ofMinutes(4)));

            return ResponseEntity.ok(Map.of("message", "Las notas se enviaron a sellar correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al sellar las notas: " + e.getMessage()));
        }
    }

    @PostMapping("/sellar-temp")
    public ResponseEntity<Map<String, String>> sellarNotas() {
        try {
            List<Nota> notas = notaService.findNotasParaSellar();
            int cantidad = tsaService.generarNotaRequest(notas);
            System.out.println("Notas registradas para TSA: " + cantidad);
            int cantidad2 = bfaTsaService.primerSelloABFA();
            System.out.println("Notas selladas en BFA: " + cantidad2);

            scheduler.schedule(
                    () -> bfaTsaService.generarRecibosDefinitivos(),
                    Instant.now().plus(Duration.ofMinutes(4)));

            return ResponseEntity.ok(Map.of("message", "Las notas se enviaron a sellar correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al sellar las notas: " + e.getMessage()));
        }
    }

    @PostMapping("/sellar-definitivo")
    public ResponseEntity<String> generarRecibos() {
        try {
            int cantidad = bfaTsaService.generarRecibosDefinitivos();
            return ResponseEntity.ok("✅ " + cantidad + " recibos definitivos generados");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error: " + e.getMessage());
        }

    }

    @PostMapping("/{cursoId}/upload-acta")
    public ResponseEntity<Map<String, String>> upload(@PathVariable UUID cursoId, @RequestParam("file") MultipartFile file) throws IOException {

        String uploadsDir = "uploads/actas/";
        File dir = new File(uploadsDir);
        if (!dir.exists())
            dir.mkdirs();

        String filename = cursoId.toString() + ".jpg";
        Path path = Paths.get(uploadsDir + filename);

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        Map<String, String> response = new HashMap<>();
        response.put("url", path.toString());
        response.put("filename", filename);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{notaId}/json")
    public ResponseEntity<byte[]> descargarJson(@PathVariable UUID notaId) {
        String contenido = bfaTsaService.getJsonByNotaId(notaId);
        byte[] bytes = contenido.getBytes(StandardCharsets.UTF_8);
        String nameFile = notaId.toString() + ".json";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nameFile)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bytes);
    }

    @GetMapping("/{notaId}/rd")
    public ResponseEntity<byte[]> descargarDefinitiveRd(@PathVariable UUID notaId) {
        String contenido = bfaTsaService.getRdByNotaId(notaId);
        byte[] bytes = contenido.getBytes(StandardCharsets.UTF_8);
        String nameFile = notaId.toString() + "_sello.rd";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nameFile)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bytes);
    }

}
