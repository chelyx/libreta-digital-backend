package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, UUID> {

}
