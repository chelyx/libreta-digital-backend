package com.g5311.libretadigital.model.dto;

import java.util.Set;

public class CursoDto {

    private String docenteAuth0Id;        // opcional si tomás el docente del JWT
    private String nombre;                // si tu entidad lo usa
    private String codigo;                // si tu entidad lo usa

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

}