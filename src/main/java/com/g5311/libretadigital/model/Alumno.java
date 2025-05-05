package com.g5311.libretadigital.model;

import com.g5311.libretadigital.model.dto.AlumnoDto;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ALUMNO")
public class Alumno extends Persona {
    private Integer añoInscripto;

    public Integer getAñoInscripto() {
        return añoInscripto;
    }

    public void setAñoInscripto(Integer añoInscripto) {
        this.añoInscripto = añoInscripto;
    }

    public static Alumno fromDto(AlumnoDto dto) {
        Alumno alumno = new Alumno();
        alumno.cargarDatosBase(dto);
        alumno.setAñoInscripto(dto.getAñoInscripto());
        return alumno;
    }

}
