package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Curso;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {

    List<Curso> findByDocenteAuth0Id(String docenteAuth0Id);

    Optional<Curso> findByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCase(String codigo);
    Optional<Curso> findByCodigoAndFecha(String codigo, LocalDate fecha);

     boolean existsByIdAndAlumnos_Auth0Id(UUID cursoId, String alumnoId);

    List<Curso> findByDocenteAuth0IdAndFecha(String docenteAuth0Id, LocalDate fecha);

}
