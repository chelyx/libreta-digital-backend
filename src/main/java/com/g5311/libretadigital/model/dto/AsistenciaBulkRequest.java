package com.g5311.libretadigital.model.dto;

import java.time.LocalDate;
import java.util.List;

public class AsistenciaBulkRequest {
    private Long cursoId;
    private LocalDate fecha;
    private List<AsistenciaAlumnoDto> asistencias;

    // getters y setters
    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<AsistenciaAlumnoDto> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<AsistenciaAlumnoDto> asistencias) {
        this.asistencias = asistencias;
    }
}
