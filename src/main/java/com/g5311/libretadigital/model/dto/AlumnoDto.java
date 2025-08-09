package com.g5311.libretadigital.model.dto;

import java.util.UUID;

public class AlumnoDto extends PersonaDto {
    String legajo;

    // Constructor vacío (requerido por JPA)
    public AlumnoDto() {
    }

    public AlumnoDto(UUID id, String nombre, String apellido, String mail, String legajo) {
    }
}
