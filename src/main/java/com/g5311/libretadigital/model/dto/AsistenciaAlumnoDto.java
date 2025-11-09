package com.g5311.libretadigital.model.dto;

import java.time.LocalDate;

public class AsistenciaAlumnoDto {
    private String alumnoId;
    private boolean presente;
    private LocalDate fecha;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}