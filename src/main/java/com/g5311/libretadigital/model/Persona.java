package com.g5311.libretadigital.model;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.g5311.libretadigital.model.dto.PersonaDto;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Persona {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String nombre;
    private String apellido;
    private String email;

    @Column(name = "type", insertable = false, updatable = false)
    private String type;

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String mail) {
        this.email = email;
    }

    protected void cargarDatosBase(PersonaDto dto) {
        this.nombre = dto.nombre;
        this.apellido = dto.apellido;
        this.email = dto.email;
    }
}
