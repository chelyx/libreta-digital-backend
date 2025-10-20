package com.g5311.libretadigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g5311.libretadigital.model.Asistencia;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface AsistenciaRepository extends JpaRepository<Asistencia, UUID> {
    Optional<Asistencia> findByCursoIdAndAlumnoIdAndFecha(Long cursoId, String alumnoId, LocalDate fecha);
}
