package com.g5311.libretadigital.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.Alumno;
import com.g5311.libretadigital.model.Persona;
import com.g5311.libretadigital.model.Profesor;
import com.g5311.libretadigital.model.dto.AlumnoDto;
import com.g5311.libretadigital.model.dto.ProfesorDto;
import com.g5311.libretadigital.repository.PersonaRepository;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public List<Persona> getAllAlumnos() {
        return personaRepository.findByType("ALUMNO");
    }

    public List<Persona> getAllProfesores() {
        return personaRepository.findByType("PROFESOR");
    }

    public ResponseEntity<Alumno> saveAlumno(AlumnoDto alumnoDto) {
        // Convertir AlumnoDto a Alumno
        Alumno alumno = Alumno.fromDto(alumnoDto);
        Alumno savedAlumno = personaRepository.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAlumno);
    }

    public ResponseEntity<Profesor> saveProfesor(ProfesorDto profesorDto) {
        // Convertir ProfesorDto a Alumno
        Profesor profesor = Profesor.fromDto(profesorDto);
        Profesor savedProfesor = personaRepository.save(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfesor);
    }
}