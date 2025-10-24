package com.g5311.libretadigital.model.dto;

import java.util.List;
import java.util.UUID;

public class AsistenciaBulkRequest {
    private UUID cursoId;
    private List<AsistenciaAlumnoDto> asistencias;

    // getters y setters
    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public List<AsistenciaAlumnoDto> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<AsistenciaAlumnoDto> asistencias) {
        this.asistencias = asistencias;
    }
}
