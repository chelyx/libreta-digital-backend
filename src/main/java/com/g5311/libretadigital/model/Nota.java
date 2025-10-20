package com.g5311.libretadigital.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notas")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(name = "alumno_auth0_id", nullable = false)
    private String alumnoAuth0Id;

    private String descripcion; // Ej: "Parcial 1", "TP 2", "Final"

    private Double valor; // Ej: 8.5

    private LocalDate fecha = LocalDate.now();

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getAlumnoAuth0Id() {
        return alumnoAuth0Id;
    }

    public void setAlumnoAuth0Id(String alumnoAuth0Id) {
        this.alumnoAuth0Id = alumnoAuth0Id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
