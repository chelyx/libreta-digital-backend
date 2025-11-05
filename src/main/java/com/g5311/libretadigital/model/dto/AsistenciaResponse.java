package com.g5311.libretadigital.model.dto;

import java.util.Date;
import java.util.UUID;

public class AsistenciaResponse {
    private UUID cursoId;
    private Date fecha;
    private String auth0Id;
    private String nombre;
    private boolean presente;

    // Constructor for JPQL constructor expression
    public AsistenciaResponse(UUID cursoId, Date fecha, String auth0Id, String nombre, boolean presente) {
        this.cursoId = cursoId;
        this.fecha = fecha;
        this.auth0Id = auth0Id;
        this.nombre = nombre;
        this.presente = presente;
    }

    // getters y setters
    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

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

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

}
