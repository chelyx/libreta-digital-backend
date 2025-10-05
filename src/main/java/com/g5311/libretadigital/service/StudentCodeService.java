package com.g5311.libretadigital.service;

import java.lang.StackWalker.Option;
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

    public void validateCode(String code, String studentId) {
        StudentCode sc = repo.findByCode(code);
        if (sc.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Código expirado");
        }
        if (sc.isUsed()) {
            throw new IllegalArgumentException("Código ya usado");
        }

        if (!sc.getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("Código no pertenece al usuario");
        }

    }

    public String validateCodeForAnyUser(String code) {
        Optional<StudentCode> optionalSc = Optional.ofNullable(repo.findByCode(code));

        StudentCode sc = optionalSc.orElseThrow(() -> new IllegalArgumentException("Código no encontrado"));
        if (sc.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Código expirado");
        }

        if (sc.isUsed()) {
            throw new IllegalArgumentException("Código ya usado");
        }

        sc.setUsed(true);
        repo.save(sc);

        return sc.getStudentId(); // este es el alumno dueño del código
    }

}
