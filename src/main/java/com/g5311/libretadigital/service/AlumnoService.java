package com.g5311.libretadigital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.Alumno;
import com.g5311.libretadigital.model.dto.AlumnoDto;
import com.g5311.libretadigital.repository.AlumnoRepository;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno saveAlumno(AlumnoDto alumnoDto) {
        // Convertir AlumnoDto a Alumno
        Alumno alumno = Alumno.fromDto(alumnoDto);

        return alumnoRepository.save(alumno);
    }
}