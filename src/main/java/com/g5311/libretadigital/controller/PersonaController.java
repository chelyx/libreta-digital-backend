package com.g5311.libretadigital.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.g5311.libretadigital.model.AlumnoAula;
import com.g5311.libretadigital.repository.AlumnoAulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.g5311.libretadigital.model.Alumno;
import com.g5311.libretadigital.model.Persona;
import com.g5311.libretadigital.model.Profesor;
import com.g5311.libretadigital.model.dto.AlumnoDto;
import com.g5311.libretadigital.model.dto.ProfesorDto;
import com.g5311.libretadigital.service.PersonaService;

@RestController
@RequestMapping("/api")
public class PersonaController {
    private final PersonaService personaService;
    @Autowired
    private AlumnoAulaRepository alumnoAulaRepository;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    // Create a new alumno
    @PostMapping("/alumnos/create")
    public ResponseEntity<Alumno> createAlumno(@RequestBody AlumnoDto alumnoDto) {
        return personaService.saveAlumno(alumnoDto);
    }

    // Create a new profesor
    @PostMapping("/profesores/create")
    public ResponseEntity<Profesor> createProfesor(@RequestBody ProfesorDto profesorDto) {
        return personaService.saveProfesor(profesorDto);
    }

    // Get all alumnos
    @GetMapping("/alumnos")
    public ResponseEntity<List<Persona>> getAllAlumnos() {
        List<Persona> alumnos = personaService.getAllAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    // Get all alumnos
    @GetMapping("/profesores")
    public ResponseEntity<List<Persona>> getAllProfesores() {
        List<Persona> profesores = personaService.getAllProfesores();
        return new ResponseEntity<>(profesores, HttpStatus.OK);
    }

    @GetMapping("/{id}/alumnos")
    public ResponseEntity<List<Alumno>> listarAlumnosPorAula(@PathVariable UUID id) {
        List<AlumnoAula> relaciones = alumnoAulaRepository.findByAulaId(id);

        List<Alumno> alumnos = relaciones.stream()
                .map(AlumnoAula::getAlumno)
                .map(alumno -> new Alumno(

                        alumno.getNombre(),
                        alumno.getApellido(),
                        alumno.getMail(),
                        alumno.getLegajo()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(alumnos);
    }
}
