package com.g5311.libretadigital.service;

import com.g5311.libretadigital.repository.AulaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.exception.ResourceNotFoundException;
import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.Aula;
import com.g5311.libretadigital.model.Materia;
import com.g5311.libretadigital.model.Persona;
import com.g5311.libretadigital.model.Profesor;
import com.g5311.libretadigital.model.dto.AsignarMateriaDto;
import com.g5311.libretadigital.repository.MateriaRepository;
import com.g5311.libretadigital.repository.PersonaRepository;

import java.util.List;

@Service
public class GeneralService {
    private final MateriaRepository materiaRepository;
    private final PersonaRepository personaRepository;
    private final AulaRepository aulaRepository;

    public GeneralService(MateriaRepository materiaRepository, PersonaRepository personaRepository,AulaRepository aulaRepository ) {
        this.materiaRepository = materiaRepository;
        this.personaRepository = personaRepository;
        this.aulaRepository = aulaRepository;
    }

    public ResponseEntity<String> asignarMateria(AsignarMateriaDto asignarMateriaDto) {
        // get Profesor by id
        Persona profesor = personaRepository.findById(asignarMateriaDto.idProfesor)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor not found"));
        // get Materia by id
        Materia materia = materiaRepository.findById(asignarMateriaDto.idMateria)
                .orElseThrow(() -> new ResourceNotFoundException("Materia not found"));

        // asignar materia a profesor
        if (profesor instanceof Profesor profe) {
            profe.addMateria(materia.getCodigo());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El ID proporcionado no corresponde a un profesor.");
        }
        // save profesor
        personaRepository.save(profesor);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Materia asignada correctamente a " + profesor.getNombre() + " " + profesor.getApellido());
    }
    public List<Aula> obtenerAulasPorAnio(Integer anio) {
        return aulaRepository.findByAnio(anio);
    }
}
