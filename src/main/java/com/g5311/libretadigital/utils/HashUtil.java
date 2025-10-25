package com.g5311.libretadigital.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

public class HashUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String generarHash(Object data) {
        try {
            String json = mapper.writeValueAsString(data);
            return DigestUtils.sha256Hex(json);
        } catch (Exception e) {
            throw new RuntimeException("Error generando hash", e);
        }
    }
}
