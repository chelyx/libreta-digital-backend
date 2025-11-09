package com.g5311.libretadigital.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.g5311.libretadigital.model.NotaTsa;

public interface NotaTsaRepository extends JpaRepository<NotaTsa, Long> {
    NotaTsa findByNotaId(UUID notaId);

    @Query("SELECT r FROM NotaTsa r WHERE r.status= 'pending' AND r.temporaryRd IS NULL")
    List<NotaTsa> findPendientesDeEnviar();

    @Query("SELECT r FROM NotaTsa r WHERE r.status = 'temporal' AND r.temporaryRd IS NOT NULL AND r.definitiveRd IS NULL")
    List<NotaTsa> findTemporales();

}
