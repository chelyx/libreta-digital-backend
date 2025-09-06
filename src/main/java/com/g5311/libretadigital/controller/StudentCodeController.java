package com.g5311.libretadigital.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g5311.libretadigital.service.StudentCodeService;

@RestController
@RequestMapping("/api/codes")
public class StudentCodeController {

    @Autowired
    private StudentCodeService service;

    // Alumno pide su código
    @PostMapping("/generate/{studentId}")
    public ResponseEntity<?> generate(@PathVariable String studentId) {
        String code = service.generateCode(studentId);
        return ResponseEntity.ok(Map.of("studentId", studentId, "code", code));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        return service.validateCode(code)
                .map(studentId -> ResponseEntity.ok(Map.of(
                        "valid", true,
                        "studentId", studentId)))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("valid", false)));
    }
}
