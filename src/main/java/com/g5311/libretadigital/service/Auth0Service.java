package com.g5311.libretadigital.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Auth0Service {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.clientId}")
    private String clientId;

    @Value("${auth0.clientSecret}")
    private String clientSecret;

    private String managementToken;

    @Autowired
    private UserService usuarioService;

    private String getManagementToken() {
        if (managementToken == null) {
            String url = domain + "/oauth/token";

            Map<String, String> request = new HashMap<>();
            request.put("client_id", clientId);
            request.put("client_secret", clientSecret);
            request.put("audience", domain + "/api/v2/");
            request.put("grant_type", "client_credentials");

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            managementToken = (String) response.getBody().get("access_token");
        }
        return managementToken;
    }

    public List<Map<String, Object>> getAllUsers() {
        String token = getManagementToken();
        String url = domain + "/api/v2/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        return response.getBody();
    }

    public Map<String, Object> getUserById(String userId) {
        String token = getManagementToken();
        String url = domain + "/api/v2/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void syncUsersFromAuth0() {
        String token = getManagementToken();
        List<Map<String, Object>> auth0Users = getUsers(token);

        for (var user : auth0Users) {
            String auth0Id = (String) user.get("user_id");
            String email = (String) user.get("email");
            String picture = (String) user.get("picture");
            String name = (String) user.get("name");

            usuarioService.createIfNotExists(auth0Id, email, picture, normalizarNombre(name));
        }
    }

    private String normalizarNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            return nombre;

        return Arrays.stream(nombre.toLowerCase().split(" "))
                .filter(s -> !s.isBlank())
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" "));
    }

    private List<Map<String, Object>> getUsers(String token) {
        String url = domain + "/api/v2/users?per_page=100";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        return response.getBody();
    }

}
