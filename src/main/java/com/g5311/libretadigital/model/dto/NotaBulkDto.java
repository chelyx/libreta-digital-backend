package com.g5311.libretadigital.model.dto;

import java.util.List;
import java.util.UUID;

public class NotaBulkDto {
    private UUID cursoId;
    private List<NotaDto> notas;

    // getters y setters
    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public List<NotaDto> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDto> notas) {
        this.notas = notas;
    }

}
