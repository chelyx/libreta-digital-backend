package com.g5311.libretadigital.model.dto;

import java.util.List;

public class HistorialAsistenciaDto {
    private List<String> fechas;
    private List<AlumnoAsistenciaDto> alumnos;

    public List<String> getFechas() {
        return fechas;
    }

    public void setFechas(List<String> fechas) {
        this.fechas = fechas;
    }

    public List<AlumnoAsistenciaDto> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<AlumnoAsistenciaDto> alumnos) {
        this.alumnos = alumnos;
    }
}
