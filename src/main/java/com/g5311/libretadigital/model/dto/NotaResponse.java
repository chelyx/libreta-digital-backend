package com.g5311.libretadigital.model.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class NotaResponse {

    private UUID id;
    private UUID cursoId;
    private LocalDate fecha;
    private String alumnoNombre;
    private String descripcion;
    private Double valor;
    private boolean presente;

    // 👇 Constructor usado por el JPQL
    public NotaResponse() {
    }

    public NotaResponse(UUID id, UUID cursoId, LocalDate fecha, Double valor,
            String descripcion, boolean presente) {
        this.id = id;
        this.cursoId = cursoId;
        this.fecha = fecha;
        this.valor = valor;
        this.descripcion = descripcion;
        this.presente = presente;
    }

    public NotaResponse(UUID id, UUID cursoId, LocalDate fecha, String nombre, Double valor,
            String descripcion, boolean presente) {
        this.id = id;
        this.cursoId = cursoId;
        this.fecha = fecha;
        this.alumnoNombre = nombre;
        this.valor = valor;
        this.descripcion = descripcion;
        this.presente = presente;
    }

    // getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getAlumnoNombre() {
        return alumnoNombre;
    }

    public void setAlumnoNombre(String alumnoNombre) {
        this.alumnoNombre = alumnoNombre;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

}
