package com.g5311.libretadigital.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "notas")
public class Nota {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "curso_id", nullable = false)
    private UUID cursoId;

    @Column(name = "alumno_auth0_id", nullable = false)
    private String alumnoAuth0Id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion; // Ej: "Parcial 1", "TP 2", "Final"

    @Column(name = "valor")
    private Double valor; // Ej: 8.5

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha = LocalDate.now();

    @Column(name = "presente", nullable = false)
    private boolean presente;

    @Column(name = "version_hash")
    private String versionHash;

    @PrePersist
    @PreUpdate
    private void autoVersionHash() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> valores = new HashMap<>();
            valores.put("valor", valor);
            valores.put("alumnoAuth0Id", alumnoAuth0Id);
            valores.put("cursoId", cursoId);
            valores.put("fecha", fecha.toString());

            String json = mapper.writeValueAsString(valores);
            this.versionHash = DigestUtils.sha256Hex(json);

        } catch (Exception e) {
            throw new RuntimeException("Error generando versionHash", e);
        }
    }

    // --- Getters y Setters ---
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID curso) {
        this.cursoId = curso;
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

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public String getVersionHash() {
        return versionHash;
    }

    public void setVersionHash(String versionHash) {
        this.versionHash = versionHash;
    }
}
