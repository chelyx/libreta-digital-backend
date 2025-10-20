package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByDocenteAuth0Id(String docenteAuth0Id);
}
