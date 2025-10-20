package com.g5311.libretadigital.model.dto;

public class AsistenciaAlumnoDto {
    private String alumnoId;
    private boolean presente;

    // getters y setters
    public String getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}
