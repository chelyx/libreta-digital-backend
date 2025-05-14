package com.g5311.libretadigital.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g5311.libretadigital.model.Asistencia;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, UUID> {

}
