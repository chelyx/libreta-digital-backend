package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.NotaResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NotaRepository extends JpaRepository<Nota, UUID> {

    List<Nota> findByCursoId(UUID cursoId);

    List<Nota> findByCursoIdAndAlumnoAuth0Id(UUID cursoId, String alumnoAuth0Id);

    List<Nota> findByAlumnoAuth0Id(String alumnoAuth0Id);

    @Query("select new com.g5311.libretadigital.model.dto.NotaResponse(a.id,a.cursoId, a.fecha, u.auth0Id, u.nombre, a.valor, a.descripcion) "
            + "from Nota a join com.g5311.libretadigital.model.User u on a.alumnoAuth0Id = u.auth0Id "
            + "where a.cursoId = :cursoId")
    List<NotaResponse> findNotaResponsesByCursoId(@Param("cursoId") UUID cursoId);

}