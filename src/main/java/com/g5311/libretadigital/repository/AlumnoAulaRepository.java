package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.AlumnoAula;
import com.g5311.libretadigital.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlumnoAulaRepository extends JpaRepository<AlumnoAula, UUID> {
}
