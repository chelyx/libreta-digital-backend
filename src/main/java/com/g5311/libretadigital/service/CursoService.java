package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.model.dto.CursoDto;
import com.g5311.libretadigital.repository.CursoRepository;
import com.g5311.libretadigital.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final UserRepository userRepository;

    public CursoService(CursoRepository cursoRepository, UserRepository userRepository) {
        this.cursoRepository = cursoRepository;
        this.userRepository = userRepository;
    }

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public List<Curso> obtenerCursosPorDocente(String docenteAuth0Id) {
        return cursoRepository.findByDocenteAuth0Id(docenteAuth0Id);
    }

    public List<Curso> obtenerCursosPorDocenteYFecha(String docenteAuth0Id, LocalDate fecha) {
        return cursoRepository.findByDocenteAuth0IdAndFecha(docenteAuth0Id, fecha);
    }

    @Transactional
    public Curso crearCurso(CursoDto dto, String docenteAuth0IdFromJwtIfAny) throws ParseException {
        Curso c = new Curso();

        // prioridad al JWT; si no hay, usa el body
        String docenteId = (docenteAuth0IdFromJwtIfAny != null && !docenteAuth0IdFromJwtIfAny.isBlank())
                ? docenteAuth0IdFromJwtIfAny
                : dto.getDocenteAuth0Id();

        if (docenteId == null || docenteId.isBlank()) {
            throw new IllegalArgumentException("docenteAuth0Id no provisto");
        }
        c.setDocenteAuth0Id(docenteId);

        if (dto.getNombre() != null)
            c.setNombre(dto.getNombre());
        if (dto.getCodigo() != null)
            c.setCodigo(dto.getCodigo());
        if (dto.getFecha() != null)
            c.setFecha(dto.getFecha());
        return cursoRepository.save(c);
    }

    public Curso obtenerPorCodigo(String codigo) {
        return cursoRepository.findByCodigoIgnoreCase(codigo.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado…" + codigo));
    }

    public Optional<Curso> findByCodigoAndFecha(String codigo, LocalDate fecha) {
        return cursoRepository.findByCodigoAndFecha(codigo, fecha);
    }

    public List<Curso> findAllByFecha(LocalDate fecha) {
        return cursoRepository.findByFecha(fecha);
    }

    // --- Mapper básico. Reemplaza por MapStruct si lo usas en el proyecto ---
    public CursoDto toDto(Curso c) {
        CursoDto dto = new CursoDto();
        dto.setNombre(c.getNombre());
        dto.setCodigo(c.getCodigo());
        dto.setFecha(c.getFecha() != null ? c.getFecha() : null); // LocalDate -> 'YYYY-MM-DD'
        dto.setDocenteAuth0Id(c.getDocenteAuth0Id());
        return dto;
    }

    public List<CursoDto> toDtoList(List<Curso> cursos) {
        return cursos.stream().map(this::toDto).toList();
    }

    public boolean alumnoRegistradoEnCurso(UUID cursoId, String alumnoId) {
        return cursoRepository.existsByIdAndAlumnos_Auth0Id(cursoId, alumnoId);
    }
}
