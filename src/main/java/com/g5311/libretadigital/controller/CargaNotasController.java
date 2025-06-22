package com.g5311.libretadigital.controller;


import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.service.CargaNotasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CargaNotasController {
    private final CargaNotasService notasService;

    public CargaNotasController(CargaNotasService notasService) {
        this.notasService = notasService;
    }

    // Create a new nota
    @PostMapping("/notas/create")
    public ResponseEntity<Nota> createAlumno(@RequestBody NotaDto notaDto) {
        return notasService.saveNota(notaDto);
    }

    // Get all notas
    @GetMapping("/notas")
    public ResponseEntity<List<Nota>> getAllAlumnos() {
        List<Nota> notas = notasService.getAllNotas();
        return new ResponseEntity<>(notas, HttpStatus.OK);
    }

}