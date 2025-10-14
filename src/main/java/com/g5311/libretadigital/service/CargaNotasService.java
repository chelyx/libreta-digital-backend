package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.*;
import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.repository.MateriaRepository;
import com.g5311.libretadigital.repository.NotaRepository;

import com.g5311.libretadigital.repository.PersonaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargaNotasService {
    private final NotaRepository notaRepository;
    private final MateriaService materiaService;
    private final PersonaService personaService;

    public CargaNotasService(NotaRepository notaRepository, MateriaService materiaService,PersonaService personaService) {
        this.notaRepository = notaRepository;
        this.materiaService = materiaService;
        this.personaService=personaService;
    }

    public List<Nota> getAllNotas() {
        return notaRepository.findAll();
    }

    public ResponseEntity<Nota> saveNota(NotaDto notaDto) {
        // Convertir NotaDto a Nota
        //TODO: agregar log para encontrar error
        Alumno alumno =  ((Alumno) personaService.getPersonById(notaDto.idAlumno).stream().toList().get(0));
        Profesor profesor =  ((Profesor) personaService.getPersonById(notaDto.idProfesor).stream().toList().get(0));
        Materia materia = ((Materia) materiaService.getMateriaById(notaDto.idMateria).stream().toList().get(0));
        Nota nota = Nota.fromDto(notaDto, alumno, profesor, materia);
        Nota savedNota = notaRepository.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNota);
    }

}
