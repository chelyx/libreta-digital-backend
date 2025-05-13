package com.g5311.libretadigital.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g5311.libretadigital.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    List<Persona> findByType(String type);
    Persona findByEmail(String email);

}
