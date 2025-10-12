package com.g5311.libretadigital.controller;

import java.util.List;
import java.util.Map;

import com.g5311.libretadigital.model.Aula;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.g5311.libretadigital.model.dto.AsignarMateriaDto;
import com.g5311.libretadigital.service.GeneralService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class GeneralController {

    private final GeneralService generalService;

    public GeneralController(GeneralService generalService) {
        this.generalService = generalService;
    }

    // asignar materia a profesor
    @PostMapping("/asignar-materia")
    public ResponseEntity<String> asignarMateria(@RequestBody AsignarMateriaDto asignarMateriaDto) {
        return generalService.asignarMateria(asignarMateriaDto);
    }

    @GetMapping("/roles")
    public Map<String, Object> getProfile(@AuthenticationPrincipal Jwt jwt) {
        // Leemos los claims que necesitamos
        Object rolesClaim = jwt.getClaim("https://sirca.com/roles");

        return Map.of(
                "userId", jwt.getSubject(),
                "roles", rolesClaim);
    }
    @GetMapping("/aulas")
    public ResponseEntity<List<Aula>> obtenerAulasPorAnio(@RequestParam Integer anio) {
        List<Aula> aulas = generalService.obtenerAulasPorAnio(anio);
        return aulas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(aulas);
    }
}
