package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.Materia;
import com.g5311.libretadigital.model.dto.AsistenciaDetalleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, UUID> {

    @Query(value = """
        SELECT 
            d.codigo   AS codigo,
            d.cuatri   AS cuatri,
            d.anio     AS anio,
            c.fecha    AS fecha,
            c.estado   AS estado
        FROM ALUMNOAULA_ASISTENCIA a
        JOIN ALUMNO_AULA b ON a.ALUMNOAULA_ID = b.ID
        JOIN ASISTENCIA   c ON c.ID = a.ASISTENCIA_ID
        JOIN AULA         d ON b.AULA_ID = d.ID
        WHERE b.ALUMNO_ID = :alumnoId
        """, nativeQuery = true)
    List<AsistenciaDetalleView> findAsistenciaDetalleByAlumnoId(@Param("alumnoId") UUID alumnoId);
}
