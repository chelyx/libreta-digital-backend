package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotaRepository extends JpaRepository<Nota, UUID> {

    @Query("SELECT n FROM Nota n WHERE n.fecha = :fecha AND n.alumno.id = :alumnoId")
    List<Nota> findByFechaAndAlumnoId(@Param("fecha") String fecha, @Param("alumnoId") UUID alumnoId);

}
