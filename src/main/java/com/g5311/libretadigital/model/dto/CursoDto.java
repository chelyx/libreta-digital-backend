package com.g5311.libretadigital.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Set;

public class CursoDto {

    private String docenteAuth0Id; // opcional si tomás el docente del JWT
    private String nombre; // si tu entidad lo usa
    private String codigo; // si tu entidad lo usa
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha;

    public CursoDto() {
    }

    public String getDocenteAuth0Id() {
        return docenteAuth0Id;
    }

    public void setDocenteAuth0Id(String docenteAuth0Id) {
        this.docenteAuth0Id = docenteAuth0Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}