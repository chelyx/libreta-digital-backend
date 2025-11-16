package com.g5311.libretadigital.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String nombre;

    @Column(nullable = false, unique = true)
    private String codigo; // Ej: "SIS101" o "INF3A"

    @Column(name = "docente_auth0_id")
    private String docenteAuth0Id; // para vincular al profe en Auth0

    @ManyToMany
    @JoinTable(name = "curso_alumno", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "alumno_auth0_id"))
    private Set<User> alumnos = new HashSet<>();

    @Column(name = "esFinal", nullable = false)
    private Boolean esFinal;

    @Column(name = "Fecha", nullable = true)
    private LocalDate fecha;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDocenteAuth0Id() {
        return docenteAuth0Id;
    }

    public void setDocenteAuth0Id(String docenteAuth0Id) {
        this.docenteAuth0Id = docenteAuth0Id;
    }

    public Set<User> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Set<User> alumnos) {
        this.alumnos = alumnos;
    }

    public Boolean getEsFinal() {return esFinal;}

    public void setEsFinal(Boolean esFinal) {this.esFinal = esFinal;}

    public LocalDate getFecha() {return fecha;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

}