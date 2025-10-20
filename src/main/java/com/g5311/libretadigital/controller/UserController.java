package com.g5311.libretadigital.controller;

import com.g5311.libretadigital.model.User;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.g5311.libretadigital.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@AuthenticationPrincipal Jwt jwt) {
        return userService.registerIfNotExists(jwt);
    }

    @GetMapping("/roles")
    public Map<String, Object> getProfile(@AuthenticationPrincipal Jwt jwt) {
        // Leemos los claims que necesitamos
        Object rolesClaim = jwt.getClaim("https://sirca.com/roles");

        return Map.of(
                "userId", jwt.getSubject(),
                "roles", rolesClaim);
    }

}
