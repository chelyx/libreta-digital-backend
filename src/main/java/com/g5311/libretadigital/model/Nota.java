package com.g5311.libretadigital.model;

import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.model.dto.PersonaDto;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
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

    public Nota(String fecha, Alumno alumno, Profesor profesor, Materia materia, int valor) {
        this.fecha = fecha;
        this.alumno = alumno;
        this.profesor = profesor;
        this.materia = materia;
        this.valor = valor;
    }

    public static Nota fromDto(NotaDto notaDto, Alumno al, Profesor prof, Materia mat) {
       Nota nota = new Nota();
       nota.cargarDatosBase(notaDto, al, prof, mat);
        return nota;
    }

    protected void cargarDatosBase(NotaDto dto, Alumno al, Profesor prof, Materia mat) {
        LocalDateTime fechaHora = LocalDateTime.now();
        this.fecha = String.valueOf(fechaHora);
        this.alumno = al;
        this.profesor = prof;
        this.materia = mat;
        this.valor = dto.valor;
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
