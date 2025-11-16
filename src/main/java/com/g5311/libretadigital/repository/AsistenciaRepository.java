package com.g5311.libretadigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.dto.AsistenciaResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AsistenciaRepository extends JpaRepository<Asistencia, UUID> {
    Optional<Asistencia> findByCursoIdAndAlumnoIdAndFecha(UUID cursoId, String alumnoId, LocalDate fecha);

    List<Asistencia> findByCursoId(UUID cursoId);

    @Query("select new com.g5311.libretadigital.model.dto.AsistenciaResponse(a.cursoId, a.fecha, u.auth0Id, u.nombre, a.presente) "
            + "from Asistencia a join com.g5311.libretadigital.model.User u on a.alumnoId = u.auth0Id "
            + "where a.cursoId = :cursoId")
    List<AsistenciaResponse> findAsistenciaResponsesByCursoId(@Param("cursoId") UUID cursoId);

     boolean existsByCursoId(UUID cursoId);
}
