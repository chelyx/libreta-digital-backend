package com.g5311.libretadigital.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

}
