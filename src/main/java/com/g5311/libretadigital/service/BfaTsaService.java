package com.g5311.libretadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.NotaTsa;
import com.g5311.libretadigital.repository.NotaRepository;
import com.g5311.libretadigital.repository.NotaTsaRepository;
import com.g5311.libretadigital.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BfaTsaService {

    @Autowired
    private NotaTsaRepository notaTsaRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private UserRepository userRepository;

    private static final String TSA_URL = "https://tsaapi.bfa.ar/api/tsa/";

    public int primerSelloABFA() {
        List<NotaTsa> pendientes = notaTsaRepository.findPendientesDeEnviar();
        int procesadas = 0;

        for (NotaTsa request : pendientes) {
            try {
                String temporaryRd = sellarEnBfa(request.getHash());
                guardarRespuesta(request, temporaryRd, "temporal");
                procesadas++;
            } catch (Exception e) {
                e.printStackTrace();
                guardarRespuesta(request, null, "error: " + e.getMessage());
            }
        }

        return procesadas;
    }

    private String sellarEnBfa(String hash) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(Collections.singletonMap("file_hash", hash));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(jsonBody.getBytes(StandardCharsets.UTF_8).length);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        String url = TSA_URL + "stamp/";
        ResponseEntity<Map> response = restTemplate.postForEntity(
                url,
                request,
                Map.class);

        System.out.println("Respuesta de TSA: " + response.getBody());
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Error en respuesta TSA: " + response.getStatusCode());
        }

        Object temporaryRd = response.getBody().get("temporary_rd");
        if (temporaryRd == null) {
            throw new RuntimeException("No se recibió temporary_rd");
        }

        return temporaryRd.toString();
    }

    private void guardarRespuesta(NotaTsa request, String temporaryRd, String status) {
        if (temporaryRd != null) {
            request.setTemporaryRd(temporaryRd);
            request.setStatus(status);
        }
        notaTsaRepository.save(request);
    }

    public int generarRecibosDefinitivos() {
        List<NotaTsa> pendientes = notaTsaRepository.findTemporales();
        int procesadas = 0;

        for (NotaTsa response : pendientes) {
            try {
                String definitivo = solicitarRecibo(response.getTemporaryRd(), response.getHash());
                response.setDefinitiveRd(definitivo);
                response.setStatus("success");
                notaTsaRepository.save(response);

                // Enviar por mail al alumno
                Nota nota = notaRepository.findById(response.getNota().getId()).orElse(null);
                String alumnoAuth0Id = nota.getAlumnoAuth0Id();
                String destinatario = userRepository.findById(alumnoAuth0Id)
                        .map(u -> u.getEmail())
                        .orElse("");
                enviarSelloPorMail(destinatario, response.getJsonEnviado(), definitivo);
                procesadas++;
            } catch (Exception e) {
                e.printStackTrace();
                notaTsaRepository.save(response);
            }
        }

        return procesadas;
    }

    private String solicitarRecibo(String temporaryRd, String hash) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("file_hash", hash);
        body.put("rd", temporaryRd); // agregamos el segundo parámetro

        String jsonBody = mapper.writeValueAsString(body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(jsonBody.getBytes(StandardCharsets.UTF_8).length);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        String url = TSA_URL + "verify/";
        ResponseEntity<Map> response = restTemplate.postForEntity(
                url,
                request,
                Map.class);

        System.out.println("Respuesta de TSA: " + response.getBody());

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Error en respuesta TSA: " + response.getStatusCode());
        }

        Object definitiveRd = response.getBody().get("permanent_rd");
        if (definitiveRd == null) {
            throw new RuntimeException("No se recibió permanent_rd");
        }

        return definitiveRd.toString();
    }

    public void enviarSelloPorMail(String destinatario, String jsonOriginal, String definitiveRd)
            throws Exception {
        // Convertir JSON a bytes
        byte[] jsonBytes = jsonOriginal.getBytes(StandardCharsets.UTF_8);
        ByteArrayResource jsonResource = new ByteArrayResource(jsonBytes);

        // Convertir a bytes
        ByteArrayResource rdResource = new ByteArrayResource(definitiveRd.getBytes(StandardCharsets.UTF_8));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject("Sello de Tiempo BFA - Nota sellada");
        helper.setText("""
                Hola,

                Adjuntamos la nota sellada con su correspondiente sello de tiempo (BFA).
                Archivos incluidos:
                - nota.json → contenido original sellado
                - sello_bfa_definitivo.rd → recibo de tiempo oficial

                Podés verificar ambos en https://bfa.ar/sello#tab_3

                Saludos,
                SIRCA
                """);

        helper.addAttachment("nota.json", jsonResource);
        helper.addAttachment("sello_bfa_definitivo.rd", rdResource);

        mailSender.send(message);
    }
}
