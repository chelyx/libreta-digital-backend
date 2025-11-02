package com.g5311.libretadigital.service;

import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

@Service
public class TsaService {

    private RestTemplate restTemplate = new RestTemplate();

    public TsaService() {
        // 👇 fuerza el envío con Content-Length (sin chunked transfer)
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setBufferRequestBody(true);
        this.restTemplate = new RestTemplate(factory);
    }

    public Map<String, Object> registrarHashEnTsa(String hash) {
        Map<String, Object> result = new HashMap<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(Collections.singletonMap("file_hash", hash));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentLength(jsonBody.getBytes(StandardCharsets.UTF_8).length);

            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://tsaapi.bfa.ar/api/tsa/stamp/",
                    request,
                    Map.class);

            System.out.println("Respuesta de TSA: " + response.getBody());

            String rdBase64 = (String) response.getBody().get("temporary_rd");
            getTempRdFile(response, true);

            Thread.sleep(180000);

            verifyTempRdEnTSA(hash, rdBase64);

            result = response.getBody();

        } catch (JsonProcessingException e) {
            System.err.println("❌ Error al procesar JSON: " + e.getMessage());
            result.put("status", "error");
            result.put("message", "Error al procesar JSON");
        } catch (IOException e) {
            System.err.println("❌ Error al escribir el archivo: " + e.getMessage());
            result.put("status", "error");
            result.put("message", "Error al escribir el archivo");
        } catch (Exception e) {
            System.err.println("❌ Error general: " + e.getMessage());
            result.put("status", "error");
            result.put("message", "Error general: " + e.getMessage());
        }

        return result;
    }

    private void verifyTempRdEnTSA(String hash, String tempRd) throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("file_hash", hash);
        body.put("rd", tempRd); // agregamos el segundo parámetro

        String jsonBody = mapper.writeValueAsString(body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(jsonBody.getBytes(StandardCharsets.UTF_8).length);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://tsaapi.bfa.ar/api/tsa/verify/",
                request,
                Map.class);

        System.out.println("Respuesta de TSA: " + response.getBody());

        getTempRdFile(response, false);

    }

    private void getTempRdFile(ResponseEntity<Map> response, boolean temp) throws IOException {
        String rdBase64 = (String) response.getBody().get("temporary_rd");

        if (rdBase64 != null) {
            String filename = temp ? "sello_bfa.rd.temp" : "sello_bfa.rd";
            try (FileWriter fw = new FileWriter(filename)) {
                fw.write("\"" + rdBase64 + "\"");
            }
            System.out.println("✅ Archivo sello_bfa.rd generado correctamente");
        } else {
            System.err.println("⚠️ No se recibió el campo temporary_rd");
        }
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
