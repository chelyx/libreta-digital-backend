package com.g5311.libretadigital.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Aula {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToMany(mappedBy = "aulas")
    private Set<Alumno> alumnos = new HashSet<>();
    private int anio;

    private String codigo;

    private String cuatri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    public Aula(UUID id, Materia materia, int anio, String codigo, String cuatri, Profesor profesor) {
        this.id = id;
        this.materia = materia;
        this.anio = anio;
        this.codigo = codigo;
        this.cuatri = cuatri;
        this.profesor = profesor;
    }

    public Aula() {

    }
}
