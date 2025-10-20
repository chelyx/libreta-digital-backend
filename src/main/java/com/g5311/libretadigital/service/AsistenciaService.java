package com.g5311.libretadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.repository.AsistenciaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public Asistencia registrarAsistencia(Long cursoId, String alumnoId, LocalDate fecha, boolean presente) {
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

    public void registrarAsistenciasMasivas(Long cursoId, LocalDate fecha, List<Asistencia> lista) {
        for (Asistencia asistencia : lista) {
            registrarAsistencia(cursoId, asistencia.getAlumnoId(), fecha, asistencia.getPresente());
        }
    }
}
