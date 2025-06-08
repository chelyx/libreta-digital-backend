package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Alumno;
import com.g5311.libretadigital.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, UUID> {

    List<Persona> findByType(String type);
    Persona findByEmail(String email);

}
