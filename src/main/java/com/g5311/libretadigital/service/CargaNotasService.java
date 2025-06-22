package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.*;
import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.repository.MateriaRepository;
import com.g5311.libretadigital.repository.NotaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargaNotasService {
    private final NotaRepository notaRepository;
    private final MateriaRepository materiaRepository;

    public CargaNotasService(NotaRepository notaRepository, MateriaRepository materiaRepository) {
        this.notaRepository = notaRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<Nota> getAllNotas() {
        return notaRepository.findAll();
    }

//    public List<Materia> getAllMaterias() {
//        return materiaRepository.findAll();
//    }

    public ResponseEntity<Nota> saveNota(NotaDto notaDto) {
        // Convertir NotaDto a Nota
        Nota nota = Nota.fromDto(notaDto);
        Nota savedNota = notaRepository.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNota);
    }

}
