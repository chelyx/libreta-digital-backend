package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.NotaResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface NotaRepository extends JpaRepository<Nota, UUID> {

        // List<Nota> findByCursoId(UUID cursoId);

        // @Query("select new
        // com.g5311.libretadigital.model.dto.NotaResponse(a.id,a.cursoId, a.fecha, null
        // , a.valor, a.descripcion, a.presente) "
        // + " from Nota n where n.cursoId = :cursoId and n.alumnoAuth0Id =
        // :alumnoAuth0Id")
        // List<NotaResponse> findByCursoIdAndAlumnoAuth0Id(UUID cursoId, String
        // alumnoAuth0Id);

        @Query("select new com.g5311.libretadigital.model.dto.NotaResponse(a.id,a.cursoId, c.nombre, a.fecha, a.valor, a.descripcion, a.presente) "
                        + " from Nota a join com.g5311.libretadigital.model.Curso c on a.cursoId = c.id "
                        + "where a.alumnoAuth0Id = :alumnoAuth0Id")
        List<NotaResponse> findNotaResponseByAlumnoId(String alumnoAuth0Id);

        @Query("select new com.g5311.libretadigital.model.dto.NotaResponse(a.id,a.cursoId, a.fecha, u.nombre, a.valor, a.descripcion, a.presente) "
                        + "from Nota a join com.g5311.libretadigital.model.User u on a.alumnoAuth0Id = u.auth0Id "
                        + "where a.cursoId = :cursoId")
        List<NotaResponse> findNotaResponsesByCursoId(@Param("cursoId") UUID cursoId);

        @Query("SELECT n FROM Nota n WHERE n.fecha <= :limite AND n.id NOT IN (SELECT r.nota.id FROM com.g5311.libretadigital.model.NotaTsa r)")
        List<Nota> findNotasParaSellar(@Param("limite") LocalDate limite);


        boolean existsByCursoId(UUID cursoId);

}