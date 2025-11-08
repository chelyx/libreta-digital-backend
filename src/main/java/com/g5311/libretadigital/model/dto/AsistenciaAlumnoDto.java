package com.g5311.libretadigital.model.dto;

import java.util.Date;

public class AsistenciaAlumnoDto {
    private String alumnoId;
    private boolean presente;
    private Date fecha;

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}