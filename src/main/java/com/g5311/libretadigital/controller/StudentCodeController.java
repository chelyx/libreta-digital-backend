package com.g5311.libretadigital.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g5311.libretadigital.service.Auth0Service;
import com.g5311.libretadigital.service.StudentCodeService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/codes")
public class StudentCodeController {

    @Autowired
    private StudentCodeService service;
    @Autowired
    private Auth0Service auth0Service;

    @PostMapping("/generate")
    public ResponseEntity<?> generateCode(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject(); // el "sub" de Auth0
        String code = service.generateCode(userId);

        return ResponseEntity.ok(Map.of("code", code));
    }

    @PostMapping("/validate-self")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> body, @AuthenticationPrincipal Jwt jwt) {
        String code = body.get("code");
        String userId = jwt.getSubject();
        try {
            service.validateCode(code, userId);
            return ResponseEntity.ok(Map.of("valid", true));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("valid", false, "error", "Código no encontrado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("valid", false, "error", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('PROFESOR')") // coincide exactamente con el rol en Auth0
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> body) {
        String code = body.get("code");

        try {
            String studentId = service.validateCodeForAnyUser(code);
            Map<String, Object> user = auth0Service.getUserById(studentId);

            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("valid", false, "error", "Código no encontrado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("valid", false, "error", e.getMessage()));
        }
    }

}
