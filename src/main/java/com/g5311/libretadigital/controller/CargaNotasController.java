package com.g5311.libretadigital.controller;


import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.Materia;
import com.g5311.libretadigital.model.dto.MateriaDto;
import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.service.CargaNotasService;
import com.g5311.libretadigital.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CargaNotasController {
    private final CargaNotasService notasService;
    private final MateriaService materiaService;

    public CargaNotasController(CargaNotasService notasService, MateriaService materiaServ) {
        this.notasService = notasService;
        this.materiaService = materiaServ;
    }

    // Create a new materia
    @PostMapping("/materia/create")
    public ResponseEntity<Materia> createMateria(@RequestBody MateriaDto materiaDto) {
        return materiaService.saveMateria(materiaDto);
    }

    // Create a new nota
    @PostMapping("/notas/create")
    public ResponseEntity<Nota> createNota(@RequestBody NotaDto notaDto) {
        return notasService.saveNota(notaDto);
    }

    // Get all notas
    @GetMapping("/notas")
    public ResponseEntity<List<Nota>> getAllNotas() {
        List<Nota> notas = notasService.getAllNotas();
        return new ResponseEntity<>(notas, HttpStatus.OK);
    }

   /* @GetMapping("/notas/{alumnoId}")
    public ResponseEntity<List<Nota>> getAllNotasById( @PathVariable UUID alumnoId) {
        List<Nota> notas = notasService.getAllNotasById(alumnoId);
        return new ResponseEntity<>(notas, HttpStatus.OK);
    }*/

    // Get all materias
    @GetMapping("/materias")
    public ResponseEntity<List<Materia>> getAllMaterias() {
        List<Materia> materias = materiaService.getAllMaterias();
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }


}