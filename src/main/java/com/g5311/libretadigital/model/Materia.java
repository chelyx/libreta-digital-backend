package com.g5311.libretadigital.model;

import java.util.UUID;

import com.g5311.libretadigital.model.dto.MateriaDto;
import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.model.dto.PersonaDto;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
public class Materia {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String nombre;
    private String codigo;

    // Constructor vacío (requerido por JPA)
    public Materia() {
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

    public Materia(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public static Materia fromDto(MateriaDto materiaDto) {
        Materia materia = new Materia();
        materia.cargarDatosBase(materiaDto);
        return materia;
    }

    protected void cargarDatosBase(MateriaDto dto) {
        this.nombre = dto.nombre;
        this.codigo = dto.codigo;
    }
}
