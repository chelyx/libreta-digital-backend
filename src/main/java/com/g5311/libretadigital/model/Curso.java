package com.g5311.libretadigital.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String codigo; // Ej: "SIS101" o "INF3A"

    @Column(name = "docente_auth0_id")
    private String docenteAuth0Id; // para vincular al profe en Auth0

    @ManyToMany
    @JoinTable(name = "curso_alumno", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "alumno_auth0_id"))
    private Set<User> alumnos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}