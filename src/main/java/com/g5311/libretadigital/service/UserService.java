package com.g5311.libretadigital.service;

import java.util.Map;

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

    public void createIfNotExists(String auth0Id, String email, String picture, String name) {
        if (!usuarioRepository.existsByAuth0Id(auth0Id)) {
            User u = new User();
            u.setAuth0Id(auth0Id);
            u.setEmail(email);
            u.setPicture(picture);
            u.setNombre(name);
            u.setLegajo(LEGAJOS.getOrDefault(u.getEmail(), null));

            usuarioRepository.save(u);
        }
    }

    private static final Map<String, String> LEGAJOS = Map.of(
            "alumno@frba.utn.edu.ar", "1673334",
            "madelafuente@frba.utn.edu.ar", "1673335",
            "crocca@frba.utn.edu.ar", "1673336",
            "ecastiglione@frba.utn.edu.ar", "1673337",
            "yelias@frba.utn.edu.ar", "1673338",
            "asoffulto@frba.utn.edu.ar", "1673339");

    public User getUserById(String auth0Id) {
        return usuarioRepository.findById(auth0Id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
