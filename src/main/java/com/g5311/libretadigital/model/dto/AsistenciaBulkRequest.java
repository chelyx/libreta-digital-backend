package com.g5311.libretadigital.model.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AsistenciaBulkRequest {
    private UUID cursoId;
    private LocalDate fecha;
    private List<AsistenciaAlumnoDto> asistencias;

    // getters y setters
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

    public List<AsistenciaAlumnoDto> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<AsistenciaAlumnoDto> asistencias) {
        this.asistencias = asistencias;
    }
}
