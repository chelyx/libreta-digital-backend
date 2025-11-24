package com.g5311.libretadigital.model.dto;

import java.util.Map;

public class AlumnoAsistenciaDto {
    private String auth0Id;
    private String nombre;
    private Map<String, String> asistencias; // fecha -> P/A/T

    public String getAuth0Id() {
        return auth0Id;
    }

    public void setAuth0Id(String auth0Id) {
        this.auth0Id = auth0Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<String, String> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Map<String, String> asistencias) {
        this.asistencias = asistencias;
    }

}
