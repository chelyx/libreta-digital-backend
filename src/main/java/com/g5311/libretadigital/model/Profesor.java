package com.g5311.libretadigital.model;

import com.g5311.libretadigital.model.dto.ProfesorDto;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PROFESOR")
public class Profesor extends Persona {

    private String codigoMateria;

    // Constructor vacío (requerido por JPA)
    public Profesor() {
    }

    // Getters y Setters (si es necesario)
    public String getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(String materia) {
        this.codigoMateria = materia;
    }

    // TODO: revisar, toy probando nomas
    public void addMateria(String materiaCode) {
        if (this.codigoMateria == null) {
            this.codigoMateria = materiaCode;
        } else {
            this.codigoMateria += "," + materiaCode;
        }
    }

    public static Profesor fromDto(ProfesorDto dto) {
        Profesor alumno = new Profesor();
        alumno.cargarDatosBase(dto);
        alumno.setCodigoMateria(dto.materia);
        return alumno;
    }

    public Profesor(String nombre, String apellido, String mail, String codigoMateria) {
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setMail(mail);
        this.codigoMateria = codigoMateria;
    }
}
