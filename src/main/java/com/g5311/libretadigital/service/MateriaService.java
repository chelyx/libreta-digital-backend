package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.Materia;
import com.g5311.libretadigital.model.dto.MateriaDto;
import com.g5311.libretadigital.repository.MateriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MateriaService {
    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

   public List<Materia> getAllMaterias() {
        return materiaRepository.findAll();
   }
    public List<Materia> getMateriaByCodigo(String codigo) {
        return materiaRepository.findByCodigo(codigo);
    }

    public Optional<Materia> getMateriaById(UUID id) {
        return materiaRepository.findById(id);
    }


    public ResponseEntity<Materia> saveMateria(MateriaDto materiaDto) {
        Materia materia = Materia.fromDto(materiaDto);
        Materia savedMateria = materiaRepository.save(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMateria);
    }
}
