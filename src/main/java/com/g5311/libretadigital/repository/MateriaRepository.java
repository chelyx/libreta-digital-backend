package com.g5311.libretadigital.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g5311.libretadigital.model.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, UUID> {
    // Método para buscar una materia por su ID
    public List<Materia> findByCodigo(String codigo); // Método para buscar una materia por su código
}
