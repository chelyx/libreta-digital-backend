package com.g5311.libretadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.repository.AsistenciaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public Asistencia registrarAsistencia(UUID cursoId, String alumnoId, LocalDate fecha, boolean presente) {
        // Verificar si ya existe una asistencia para ese día
        Optional<Asistencia> existente = asistenciaRepository.findByCursoIdAndAlumnoIdAndFecha(cursoId, alumnoId,
                fecha);

        if (existente.isPresent()) {
            Asistencia a = existente.get();
            a.setPresente(presente); // actualizar
            return asistenciaRepository.save(a);
        }

        Asistencia nueva = new Asistencia();
        nueva.setCursoId(cursoId);
        nueva.setAlumnoId(alumnoId);
        nueva.setFecha(fecha);
        nueva.setPresente(presente);

        return asistenciaRepository.save(nueva);
    }

    public void registrarAsistenciasMasivas(UUID cursoId, LocalDate fecha, List<Asistencia> lista) {
        for (Asistencia asistencia : lista) {
            registrarAsistencia(cursoId, asistencia.getAlumnoId(), fecha, asistencia.getPresente());
        }
    }

    public List<Asistencia> obtenerAsistenciasPorCurso(UUID cursoId) {
        return asistenciaRepository.findByCursoId(cursoId);
    }
}
