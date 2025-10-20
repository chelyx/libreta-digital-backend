package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findByCursoId(Long cursoId);

    List<Nota> findByCursoIdAndAlumnoAuth0Id(Long cursoId, String alumnoAuth0Id);
}
