package com.g5311.libretadigital.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.StudentCode;
import com.g5311.libretadigital.repository.StudentCodeRepository;

@Service
public class StudentCodeService {

    @Autowired
    private StudentCodeRepository repo;

    public String generateCode(String studentId) {
        String code = String.format("%06d", new Random().nextInt(999999));
        StudentCode sc = new StudentCode();
        sc.setStudentId(studentId);
        sc.setCode(code);
        sc.setExpiresAt(LocalDateTime.now().plusMinutes(5)); // válido 5 min
        repo.save(sc);
        return code;
    }

    public Optional<String> validateCode(String code) {
        return repo.findByCode(code)
                .filter(sc -> sc.getExpiresAt().isAfter(LocalDateTime.now()))
                // .pipe(st -> println(st))
                .map(StudentCode::getStudentId);
    }
}
