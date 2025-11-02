package com.g5311.libretadigital.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class HashUtil {

    public static String sha256(String rutaArchivo) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(rutaArchivo);
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }
        fis.close();

        byte[] bytes = digest.digest();
        // Convertir a hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        // String rutaPdf = "nota_final_juanperez.pdf";
        // String hash = sha256(rutaPdf);
        // System.out.println("Hash SHA-256 del PDF: " + hash);
    }
}
