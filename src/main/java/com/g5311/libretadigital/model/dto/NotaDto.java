package com.g5311.libretadigital.model.dto;

public class NotaDto {
    private int legajoAlumno; // No lo subas al TSA, solo lo usás para generar hash
    private String materia;
    private int nota;
    private String fecha; // formato ISO: yyyy-MM-dd

    // Getters y Setters
    public int getAlumno() {
        return legajoAlumno;
    }

    public void setAlumno(int alumnoId) {
        this.legajoAlumno = alumnoId;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
