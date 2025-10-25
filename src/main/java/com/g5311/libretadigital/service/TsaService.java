package com.g5311.libretadigital.service;

import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class TsaService {

    private RestTemplate restTemplate = new RestTemplate();

    public TsaService() {
        // 👇 fuerza el envío con Content-Length (sin chunked transfer)
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setBufferRequestBody(true);
        this.restTemplate = new RestTemplate(factory);
    }

    public Map<String, Object> registrarHashEnTsa(String hash) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(Collections.singletonMap("file_hash", hash));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(jsonBody.getBytes(StandardCharsets.UTF_8).length);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://tsaapi.bfa.ar/api/tsa/stamp/", request, Map.class);

        System.out.println("Respuesta de TSA: " + response.getBody());

        String txHash = response.getBody().get("temporary_rd").toString();
        boolean existe = TsaService.verificarHashEnBlockchain(txHash);
        if (existe) {
            System.out.println("La nota con hash " + txHash + " existe en la blockchain BFA.");
        } else {
            System.out.println("La nota con hash " + txHash + " NO existe en la blockchain BFA.");
        }

        return response.getBody();
    }

    public static boolean verificarHashEnBlockchain(String txHash) {
        String url = "https://explorer.bfa.ar/api/transaction/" + txHash;

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                // Aquí podés agregar lógica para analizar la respuesta y verificar el hash
                // Por ejemplo, buscar el campo "file_hash" en la respuesta JSON
                return responseBody.contains("\"file_hash\":");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
