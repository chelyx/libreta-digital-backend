package com.g5311.libretadigital.model.dto;

import java.util.UUID;

public class NotaDto {
    private UUID id;
    private Double valor;

    // getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
