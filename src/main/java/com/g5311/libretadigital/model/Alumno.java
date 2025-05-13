package com.g5311.libretadigital.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.g5311.libretadigital.model.dto.AlumnoDto;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ALUMNO")
public class Alumno extends Persona {

    // Constructor vacío
    public Alumno() {
    }

    private String legajo;


    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public static Alumno fromDto(AlumnoDto dto) {
        UUID legajo = UUID.randomUUID();
        Alumno alumno = new Alumno();
        alumno.cargarDatosBase(dto);
        alumno.setLegajo(legajo.toString());
        return alumno;
    }

    // Constructor con todos los campos
    public Alumno(String nombre, String apellido, String mail, String legajo) {
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setMail(mail);
        this.legajo = legajo;
    }

}
