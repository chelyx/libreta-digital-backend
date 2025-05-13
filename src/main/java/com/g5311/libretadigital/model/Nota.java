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

    private String fecha; //por ahora lo dejo asi por el tema de los tipos de dato en la base

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    private int profesorID;

    private int materiaID;

    private int valor;

    // Constructor vacío (requerido por JPA)
    public Nota() {
    }


    public Nota(String fecha, Alumno alumnoID, int profesorID, int materiaID, int valor) {
        this.fecha = fecha;
        this.alumno = alumnoID;
        this.profesorID = profesorID;
        this.materiaID = materiaID;
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




    public int getProfesorID() {
        return profesorID;
    }

    public void setProfesorID(int profesorID) {
        this.profesorID = profesorID;
    }

    public int getMateriaID() {
        return materiaID;
    }

    public void setMateriaID(int materiaID) {
        this.materiaID = materiaID;
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
