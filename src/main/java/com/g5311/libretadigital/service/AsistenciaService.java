package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.AlumnoAula;
import com.g5311.libretadigital.model.Materia;
import com.g5311.libretadigital.model.dto.MateriaDto;
import com.g5311.libretadigital.repository.AlumnoAulaRepository;
import com.g5311.libretadigital.repository.AsistenciaRepository;
import com.g5311.libretadigital.repository.MateriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AsistenciaService {
    private final AsistenciaRepository asistenciaRepository;
    private final AlumnoAulaRepository alumnoAulaRepository;

    public AsistenciaService(AsistenciaRepository asistenciaRepository, AlumnoAulaRepository alumnoAulaRepository) {
        this.asistenciaRepository = asistenciaRepository;
        this.alumnoAulaRepository = alumnoAulaRepository;
    }
/*
    public List<AlumnoAula> getAllAlumnoAula(UUID alumnoId) {
        return alumnoAulaRepository.findAllByAlumnoId(Collections.singleton(alumnoId));
    }
*/
}
