package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface NotaRepository extends JpaRepository<Nota, UUID> {

    List<Nota> findByCursoId(UUID cursoId);

    List<Nota> findByCursoIdAndAlumnoAuth0Id(UUID cursoId, String alumnoAuth0Id);
}
