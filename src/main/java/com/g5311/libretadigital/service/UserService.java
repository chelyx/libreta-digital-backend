package com.g5311.libretadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.User;
import com.g5311.libretadigital.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository usuarioRepository;

    public User registerIfNotExists(Jwt jwt) {
        String auth0Id = jwt.getSubject();

        return usuarioRepository.findById(auth0Id)
                .orElseGet(() -> {
                    User nuevo = new User();
                    nuevo.setAuth0Id(auth0Id);
                    nuevo.setNombre(jwt.getClaim("name"));
                    nuevo.setEmail(jwt.getClaim("email"));
                    nuevo.setRol(jwt.getClaim("https://sirca.com/roles"));
                    return usuarioRepository.save(nuevo);
                });
    }
}
