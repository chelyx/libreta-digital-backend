package com.g5311.libretadigital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g5311.libretadigital.model.dto.AsignarMateriaDto;
import com.g5311.libretadigital.service.GeneralService;

@RestController
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
}
