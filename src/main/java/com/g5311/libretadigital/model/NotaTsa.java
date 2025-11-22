package com.g5311.libretadigital.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "nota_tsa")
public class NotaTsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la nota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_id", nullable = false)
    private Nota nota;

    @Column(name = "status", nullable = false, length = 128)
    private String status;

    @Lob
    @Column(name = "json_enviado", nullable = false)
    private String jsonEnviado;

    @Column(name = "hash", nullable = false, length = 128)
    private String hash;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));

    @Lob
    @Column(name = "temporary_rd")
    private String temporaryRd;

    @Lob
    @Column(name = "definitive_rd")
    private String definitiveRd;

    // --- Getters y setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public String getJsonEnviado() {
        return jsonEnviado;
    }

    public void setJsonEnviado(String jsonEnviado) {
        this.jsonEnviado = jsonEnviado;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getTemporaryRd() {
        return temporaryRd;
    }

    public void setTemporaryRd(String temporaryRd) {
        this.temporaryRd = temporaryRd;
    }

    public String getDefinitiveRd() {
        return definitiveRd;
    }

    public void setDefinitiveRd(String definitiveRd) {
        this.definitiveRd = definitiveRd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
