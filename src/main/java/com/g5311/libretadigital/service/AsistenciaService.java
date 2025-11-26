package com.g5311.libretadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.model.dto.AlumnoAsistenciaDto;
import com.g5311.libretadigital.model.dto.AsistenciaAlumnoDto;
import com.g5311.libretadigital.model.dto.AsistenciaResponse;
import com.g5311.libretadigital.model.dto.HistorialAsistenciaDto;
import com.g5311.libretadigital.repository.AsistenciaRepository;
import com.g5311.libretadigital.repository.CursoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public Asistencia registrarAsistencia(UUID cursoId, String alumnoId, LocalDate fecha, boolean presente) {
        // 1) Validar que el alumno pertenece al curso
        boolean pertenece = cursoRepository.existsByIdAndAlumnos_Auth0Id(cursoId, alumnoId);
        if (!pertenece) {
            // throw new RuntimeException("El alumno " + dto.getAlumnoId() + " no pertenece
            // al curso");
        }

        // 2) Verificar si ya existe una asistencia para ese día
        Asistencia asistencia = asistenciaRepository.findByCursoIdAndAlumnoIdAndFecha(cursoId, alumnoId,
                fecha).orElse(new Asistencia()); // si no existe, crear nueva

        asistencia.setCursoId(cursoId);
        asistencia.setAlumnoId(alumnoId);
        asistencia.setPresente(presente);
        asistencia.setFecha(LocalDate.now());

        return asistenciaRepository.save(asistencia);
    }

    public List<String> registrarAsistenciasMasivas(UUID cursoId, List<AsistenciaAlumnoDto> lista, String auth0Id) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validación del docente // LOS BEDELES TAMBIÉN PUEDEN REGISTRAR ASISTENCIAS   
        // if (!curso.getDocenteAuth0Id().equals(auth0Id)) {
        // throw new AccessDeniedException("No sos el docente asignado a este curso");
        // }
        List<Asistencia> entidades = new ArrayList<>();
        List<String> alumnosNoPertenecen = new ArrayList<>();
        for (AsistenciaAlumnoDto asistenciaDto : lista) {
            // 1) Validar que el alumno pertenece al curso
            boolean pertenece = cursoRepository.existsByIdAndAlumnos_Auth0Id(cursoId, asistenciaDto.getAlumnoId());
            if (!pertenece) {
                alumnosNoPertenecen.add(asistenciaDto.getAlumnoId());
                continue; // saltar este alumno
            }

            // 2) Verificar si ya existe una asistencia para ese día
            Asistencia asistencia = asistenciaRepository
                    .findByCursoIdAndAlumnoIdAndFecha(cursoId, asistenciaDto.getAlumnoId(),
                            asistenciaDto.getFecha())
                    .orElse(new Asistencia()); // si no existe, crear nueva

            asistencia.setCursoId(cursoId);
            asistencia.setAlumnoId(asistenciaDto.getAlumnoId());
            asistencia.setPresente(asistenciaDto.isPresente());
            asistencia.setFecha(asistenciaDto.getFecha());
            entidades.add(asistencia);
        }
        asistenciaRepository.saveAll(entidades);

        return alumnosNoPertenecen;
    }

    public List<AsistenciaResponse> obtenerAsistenciasPorCurso(UUID cursoId) {
        return asistenciaRepository.findAsistenciaResponsesByCursoId(cursoId);
    }

    public boolean existsByCursoId(UUID cursoId) {
        return asistenciaRepository.existsByCursoId(cursoId);
    }


    public List<AsistenciaResponse> obtenerAsistenciasPorAlumno(String alumnoAuth0Id) {
        return asistenciaRepository.findAsistenciaResponsesByAlumnoId(alumnoAuth0Id);
    }

    /**
     * Actualiza una asistencia existente, buscándola por (cursoId, alumnoId,
     * fecha).
     */
    public Asistencia actualizarAsistencia(UUID cursoId, String alumnoId, LocalDate fecha, boolean presente) {
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
            LocalDate fecha = dto.getFecha();
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

    public HistorialAsistenciaDto agruparAsistencias(List<AsistenciaResponse> asistencias) {
    // 1) Obtener fechas únicas
        List<String> fechas = asistencias.stream()
                .map(a -> a.getFecha().toString())
                .distinct()
                .sorted()
                .toList();

        // 2) Agrupar por alumno (auth0Id)
        Map<String, AlumnoAsistenciaDto> alumnosMap = new HashMap<>();

        for (AsistenciaResponse a : asistencias) {
            AlumnoAsistenciaDto alumno = alumnosMap.computeIfAbsent(
                    a.getAuth0Id(),
                    id -> {
                        AlumnoAsistenciaDto dto = new AlumnoAsistenciaDto();
                        dto.setAuth0Id(id);
                        dto.setNombre(a.getNombre());
                        dto.setAsistencias(new HashMap<>());
                        return dto;
                    }
            );

            // convertir boolean → P/A
            String estado = a.isPresente() ? "P" : "A";

            alumno.getAsistencias().put(a.getFecha().toString(), estado);
        }

        // 3) Crear respuesta final
         List<AlumnoAsistenciaDto> alumnosOrdenados = alumnosMap.values().stream()
            .sorted(Comparator.comparing(AlumnoAsistenciaDto::getNombre,
                    String.CASE_INSENSITIVE_ORDER))
            .toList();

        HistorialAsistenciaDto dto = new HistorialAsistenciaDto();
        dto.setFechas(fechas);
        dto.setAlumnos(alumnosOrdenados);

        return dto;
    }

    public void guardarHistorial(HistorialAsistenciaDto historial, UUID cursoId) {

        for (AlumnoAsistenciaDto alumno : historial.getAlumnos()) {

            for (Map.Entry<String, String> entry : alumno.getAsistencias().entrySet()) {

                String fechaStr = entry.getKey();
                String valor = entry.getValue(); // "P" o "A"

                LocalDate fecha = LocalDate.parse(fechaStr);
                boolean presente = valor.equals("P");

                var existente = asistenciaRepository
                        .findByCursoIdAndAlumnoIdAndFecha(cursoId, alumno.getAuth0Id(), fecha)
                        .orElseGet(() -> {
                            Asistencia nueva = new Asistencia();
                            nueva.setCursoId(cursoId);
                            nueva.setAlumnoId(alumno.getAuth0Id());
                            nueva.setFecha(fecha);
                            return nueva;
                        });

                // actualizar valor
                existente.setPresente(presente);

                asistenciaRepository.save(existente);
            }
        }
    }

}
