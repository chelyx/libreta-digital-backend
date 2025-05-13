package com.g5311.libretadigital.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Asistencia {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String fecha;

    private String estado; //dps podemos ver si enum

    @ManyToMany(mappedBy = "asistencias")
    private Set<AlumnoAula> alumnoAulas = new HashSet<>();


    public Asistencia(String fecha, String estado, Set<AlumnoAula> alumnoAulas) {
        this.fecha = fecha;
        this.estado = estado;
        this.alumnoAulas = alumnoAulas;
    }

    public Asistencia() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<AlumnoAula> getAlumnoAulas() {
        return alumnoAulas;
    }

    public void setAlumnoAulas(Set<AlumnoAula> alumnoAulas) {
        this.alumnoAulas = alumnoAulas;
    }
}
