package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.Materia;
import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.MateriaDto;
import com.g5311.libretadigital.repository.MateriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    public ResponseEntity<Materia> saveMateria(MateriaDto materiaDto) {
        Materia materia = Materia.fromDto(materiaDto);
        Materia savedMateria = materiaRepository.save(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMateria);
    }
}
