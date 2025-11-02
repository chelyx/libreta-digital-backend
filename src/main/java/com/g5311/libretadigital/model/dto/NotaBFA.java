package com.g5311.libretadigital.model.dto;

import java.util.Date;

public class NotaBFA {

    private Date fecha; // fecha en formato ISO 8601
    private int legajoAlumno;
    private String materia;
    private double nota;

    // Constructor vacío (necesario para Jackson u otros serializadores)
    public NotaBFA() {
    }

    // Constructor con todos los campos
    public NotaBFA(Date fecha, int legajoAlumno, String materia, double nota) {
        this.fecha = fecha;
        this.legajoAlumno = legajoAlumno;
        this.materia = materia;
        this.nota = nota;
    }

    // Getters y Setters
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getLegajoAlumno() {
        return legajoAlumno;
    }

    public void setLegajoAlumno(int legajoAlumno) {
        this.legajoAlumno = legajoAlumno;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "NotaDto{" +
                "fecha=" + fecha +
                ", legajoAlumno=" + legajoAlumno +
                ", materia='" + materia + '\'' +
                ", nota=" + nota +
                '}';
    }
}