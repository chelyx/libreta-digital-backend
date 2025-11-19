package com.g5311.libretadigital.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.NotaTsa;
import com.g5311.libretadigital.repository.NotaRepository;
import com.g5311.libretadigital.repository.NotaTsaRepository;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.UUID;

@Service
public class TsaService2 {

    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private NotaTsaRepository requestRepository;
    @Autowired
    private ObjectMapper objectMapper;
   

    public int generarNotaRequest() throws Exception {
        LocalDate limite = LocalDate.now();//.minusDays(1);
        List<Nota> notas = notaRepository.findNotasParaSellar(limite);

        for (Nota nota : notas) {
            // 1️⃣ Generar JSON
            String json = generarJsonNota(nota);

            // 2️⃣ Calcular hash
            String hash = calcularHash(json);

            // 3️⃣ Guardar en la tabla request
            NotaTsa request = new NotaTsa();
            request.setNota(nota);
            request.setJsonEnviado(json);
            request.setHash(hash);
            request.setStatus("pending");

            requestRepository.save(request);
        }

        return notas.size();
    }

    // auxiliares
    private String generarJsonNota(Nota nota) throws Exception {
        return objectMapper.writeValueAsString(new NotaJson(
                nota.getId(),
                nota.getCursoId(),
                nota.getFecha(),
                nota.getValor(),
                nota.getDescripcion(),
                nota.isPresente()));
    }

    private String calcularHash(String json) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(json.getBytes());
        try (Formatter formatter = new Formatter()) {
            for (byte b : hashBytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }

    // DTO auxiliar interno
    private record NotaJson(UUID id, UUID cursoId, LocalDate fecha, Double valor, String descripcion,
            boolean presente) {
    }

    public String obtenerJsonOriginal(UUID notaId) {
        var record = requestRepository.findByNotaId(notaId);
        // .orElseThrow(() -> new RuntimeException("JSON no encontrado"));
        return record.getJsonEnviado();
    }

}