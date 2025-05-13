package com.g5311.libretadigital.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class Nota {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String fecha; // por ahora lo dejo asi por el tema de los tipos de dato en la base

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private int valor;

    // Constructor vacío (requerido por JPA)
    public Nota() {
    }

    public Nota(String fecha, Alumno alumnoID, Profesor profesorID, Materia materia, int valor) {
        this.fecha = fecha;
        this.alumno = alumnoID;
        this.profesor = profesorID;
        this.materia = materia;
        this.valor = valor;
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

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesorID(Profesor profesorID) {
        this.profesor = profesorID;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateriaID(Materia materiaID) {
        this.materia = materia;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
