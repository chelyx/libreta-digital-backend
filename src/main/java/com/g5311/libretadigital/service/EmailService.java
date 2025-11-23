package com.g5311.libretadigital.service;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.User;
import com.g5311.libretadigital.utils.EmailTemplates;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String applyVariables(String template, Map<String, String> values) {
        return new StringSubstitutor(values).replace(template);
    }

    public void sendEmail(
            String to,
            String subject,
            String body,
            Map<String, ByteArrayResource> attachments // nombre → contenido
    ) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        if (attachments != null) {
            for (Map.Entry<String, ByteArrayResource> att : attachments.entrySet()) {
                helper.addAttachment(att.getKey(), att.getValue());
            }
        }

        mailSender.send(message);
    }

    public void enviarSelloPorMail(User destinatario, ByteArrayResource jsonOriginal, ByteArrayResource definitiveRd,
            Long notaId)
            throws Exception {
        String nameFile = notaId.toString();

        Map<String, ByteArrayResource> adjuntos = new HashMap<>();
        adjuntos.put(nameFile + ".json", jsonOriginal);
        adjuntos.put(nameFile + "_sello.rd", definitiveRd);

        String body = applyVariables(
                EmailTemplates.BFA_SELLO,
                Map.of(
                        "nombre", destinatario.getNombre(),
                        "json", nameFile + ".json",
                        "rd", nameFile + "_sello.rd"));
        sendEmail(
                destinatario.getEmail(),
                "Nueva nota sellada con BFA",
                body,
                adjuntos);
    }

    public void enviarNotaPorMail(String name, Nota nota, String email, String curso) throws Exception {
        String body = applyVariables(
                EmailTemplates.NOTA_ALUMNO,
                Map.of(
                        "nombre", name,
                        "curso", curso,
                        "fecha", nota.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        "nota", nota.getValor().toString(),
                        "version", nota.getVersionHash()));
        sendEmail(
                email,
                "Nueva nota cargada en SIRCA",
                body,
                null);
    }
}
