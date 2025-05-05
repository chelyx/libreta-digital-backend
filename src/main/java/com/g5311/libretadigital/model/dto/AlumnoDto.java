package com.g5311.libretadigital.model.dto;

public class AlumnoDto extends PersonaDto {

    private Integer añoInscripto;

    // Constructor vacío (requerido por JPA)
    public AlumnoDto() {
    }

    // Getters y Setters
    public Integer getAñoInscripto() {
        return añoInscripto;
    }

    public void setAñoInscripto(Integer añoInscripto) {
        this.añoInscripto = añoInscripto;
    }

}
