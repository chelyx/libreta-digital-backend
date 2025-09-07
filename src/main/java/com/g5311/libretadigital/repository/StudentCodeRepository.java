package com.g5311.libretadigital.repository;

import com.g5311.libretadigital.model.StudentCode;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCodeRepository extends JpaRepository<StudentCode, String> {
    StudentCode findByCode(String code);
}
