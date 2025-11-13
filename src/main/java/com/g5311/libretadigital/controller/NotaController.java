package com.g5311.libretadigital.controller;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.NotaBFA;
import com.g5311.libretadigital.model.dto.NotaBulkDto;
import com.g5311.libretadigital.model.dto.NotaResponse;
import com.g5311.libretadigital.service.BfaTsaService;
import com.g5311.libretadigital.service.NotaService;
import com.g5311.libretadigital.service.TsaService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/{auth0Id}") //TODO: VOLVER A PONER "/ME" y tomar el usuario del jwt. Lo cambie solo para la demo rápida.
    public List<NotaResponse> getNotasPorAlumno(@AuthenticationPrincipal Jwt jwt,@PathVariable String auth0Id) {
        //String auth0Id = jwt.getSubject();
        return notaService.obtenerNotasPorAlumno(auth0Id);
    }

    @PostMapping("/curso/{cursoId}/bulk")
    public List<Nota> guardarNotasEnBulk(
            @AuthenticationPrincipal Jwt jwt,
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

    @PostMapping("/registrar")
    public void registrarNotaBFA(@RequestBody NotaBFA entity) {
        notaService.registrarNotaTSA(entity);
    }

    @PostMapping("/sellar-temp")
    public ResponseEntity<String> sellarNotas() {
        try {
            int cantidad = tsaService.generarNotaRequest();
            System.out.println("Notas registradas para TSA: " + cantidad);
            int cantidad2 = bfaTsaService.primerSelloABFA();
            System.out.println("Notas selladas en BFA: " + cantidad2);
            return ResponseEntity.ok("✅ " + cantidad + " notas selladas (registradas para TSA)");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error: " + e.getMessage());
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

    // @GetMapping("/notas/{id}/json")
    // public ResponseEntity<String> obtenerJsonOriginal(@PathVariable UUID id) {
    // String record = tsaService.obtenerJsonOriginal(id);
    // // .orElseThrow(() -> new RuntimeException("JSON no encontrado"));
    // return ResponseEntity.ok()
    // .contentType(MediaType.APPLICATION_JSON)
    // .body(record);
    // }

    @GetMapping("/test-mail")
    public ResponseEntity<String> test() throws Exception {
        String json = "{\"alumno\": 1234, \"nota\": 10}";
        String dummyRd = "MHgtZmFrZS1iYXNlNjQ="; // simulación de Base64

        bfaTsaService.enviarSelloPorMail("arasoffulto@gmail.com", json, dummyRd);
        return ResponseEntity.ok("Mail enviado correctamente");
    }
}
