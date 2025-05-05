package com.g5311.libretadigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g5311.libretadigital.model.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}