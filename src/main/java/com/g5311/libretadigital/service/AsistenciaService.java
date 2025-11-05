package com.g5311.libretadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.dto.AsistenciaAlumnoDto;
import com.g5311.libretadigital.model.dto.AsistenciaResponse;
import com.g5311.libretadigital.repository.AsistenciaRepository;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public Asistencia registrarAsistencia(UUID cursoId, String alumnoId, Date fecha, boolean presente) {
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

    public void registrarAsistenciasMasivas(UUID cursoId, List<AsistenciaAlumnoDto> lista) {
        for (AsistenciaAlumnoDto asistencia : lista) {
            registrarAsistencia(cursoId, asistencia.getAlumnoId(), asistencia.getFecha(), asistencia.isPresente());
        }
    }

    public List<AsistenciaResponse> obtenerAsistenciasPorCurso(UUID cursoId) {
        return asistenciaRepository.findAsistenciaResponsesByCursoId(cursoId);
    }

    /**
     * Actualiza una asistencia existente, buscándola por (cursoId, alumnoId,
     * fecha).
     */
    public Asistencia actualizarAsistencia(UUID cursoId, String alumnoId, Date fecha, boolean presente) {
        Asistencia existente = asistenciaRepository
                .findByCursoIdAndAlumnoIdAndFecha(cursoId, alumnoId, fecha)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe asistencia para curso=" + cursoId + ", alumno=" + alumnoId + ", fecha=" + fecha));

        existente.setPresente(presente);
        return asistenciaRepository.save(existente);
    }

    /**
     * Actualiza varias asistencias. Si alguna no existe, arroja error detallado.
     */
    public List<Asistencia> actualizarAsistenciasMasivas(UUID cursoId, List<AsistenciaAlumnoDto> lista) {
        List<Asistencia> actualizadas = new ArrayList<>();
        for (AsistenciaAlumnoDto dto : lista) {
            Date fecha = dto.getFecha();
            String alumnoId = dto.getAlumnoId();
            boolean presente = dto.isPresente();

            Asistencia existente = asistenciaRepository
                    .findByCursoIdAndAlumnoIdAndFecha(cursoId, alumnoId, fecha)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No existe asistencia para curso=" + cursoId + ", alumno=" + alumnoId + ", fecha="
                                    + fecha));

            existente.setPresente(presente);
            actualizadas.add(asistenciaRepository.save(existente));
        }
        return actualizadas;
    }
}
