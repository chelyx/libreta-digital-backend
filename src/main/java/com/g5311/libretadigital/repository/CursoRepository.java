package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {

    List<Curso> findByDocenteAuth0Id(String docenteAuth0Id);
}
