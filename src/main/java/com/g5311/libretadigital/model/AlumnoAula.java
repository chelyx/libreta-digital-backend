package com.g5311.libretadigital.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "alumno_aula")
public class AlumnoAula {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @ManyToMany
    @JoinTable(name = "alumnoaula_asistencia", joinColumns = @JoinColumn(name = "alumnoaula_id"), inverseJoinColumns = @JoinColumn(name = "asistencia_id"))
    private Set<Asistencia> asistencias = new HashSet<>();

    public AlumnoAula(Alumno alumno, Aula aula, Set<Asistencia> asistencias) {
        this.alumno = alumno;
        this.aula = aula;
        this.asistencias = asistencias;
    }

    public AlumnoAula() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Set<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Set<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
}
