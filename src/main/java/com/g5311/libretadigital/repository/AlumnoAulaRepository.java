package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.AlumnoAula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AlumnoAulaRepository extends JpaRepository<AlumnoAula, UUID> {
     List<AlumnoAula> findByAulaId(UUID aulaId);

   //  List<AlumnoAula> findAllByAlumnoId(Set<UUID> singleton);

}
