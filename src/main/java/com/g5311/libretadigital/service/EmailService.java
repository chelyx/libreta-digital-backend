package com.g5311.libretadigital.service;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.User;
import com.g5311.libretadigital.utils.EmailTemplates;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public String applyVariables(String template, Map<String, String> values) {
        return new StringSubstitutor(values).replace(template);
    }

    /**
     * Reemplazo del viejo envío por SMTP → ahora SendGrid vía API
     */
    public void sendEmail(
            String to,
            String subject,
            String body,
            Map<String, ByteArrayResource> attachments) throws Exception {

        Email from = new Email("notasalumno@gmail.com"); // podés usar uno verificado en SendGrid
        Email recipient = new Email(to);

        Content content = new Content("text/html", body);
        Mail mail = new Mail(from, subject, recipient, content);

        // 📎 Adjuntos (respetando los ByteArrayResource originales)
        if (attachments != null) {
            for (Map.Entry<String, ByteArrayResource> att : attachments.entrySet()) {
                Attachments a = new Attachments();
                a.setContent(Base64.getEncoder().encodeToString(att.getValue().getByteArray()));
                a.setFilename(att.getKey());
                a.setType("application/octet-stream"); // genérico pero seguro
                a.setDisposition("attachment");

                mail.addAttachments(a);
            }
        }

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        System.out.println("SendGrid status: " + response.getStatusCode());
        System.out.println("SendGrid body: " + response.getBody());
    }

    /**
     * Igual que antes — cambia solo el backend del envío
     */
    public void enviarSelloPorMail(User destinatario, ByteArrayResource jsonOriginal,
            ByteArrayResource definitiveRd, Long notaId) throws Exception {

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
