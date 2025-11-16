package com.g5311.libretadigital.model.dto;

import java.util.UUID;

public class ExamenEstadoDto {
    private UUID examenId;
    private boolean asistenciaCargada;
    private boolean notasCargadas;

    public ExamenEstadoDto(UUID examenId, boolean asistenciaCargada, boolean notasCargadas) {
        this.examenId = examenId;
        this.asistenciaCargada = asistenciaCargada;
        this.notasCargadas = notasCargadas;
    }

    public UUID getExamenId() {
        return examenId;
    }

    public void setExamenId(UUID examenId) {
        this.examenId = examenId;
    }

    public boolean isAsistenciaCargada() {
        return asistenciaCargada;
    }

    public void setAsistenciaCargada(boolean asistenciaCargada) {
        this.asistenciaCargada = asistenciaCargada;
    }

    public boolean isNotasCargadas() {
        return notasCargadas;
    }

    public void setNotasCargadas(boolean notasCargadas) {
        this.notasCargadas = notasCargadas;
    }

}
