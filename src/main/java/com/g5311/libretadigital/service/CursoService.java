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

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final UserRepository userRepository;

    public CursoService(CursoRepository cursoRepository, UserRepository userRepository) {
        this.cursoRepository = cursoRepository;
        this.userRepository = userRepository;
    }

    public List<Curso> obtenerCursosPorDocente(String docenteAuth0Id) {
        return cursoRepository.findByDocenteAuth0Id(docenteAuth0Id);
    }

    @Transactional
    public Curso crearCurso(CursoDto dto, String docenteAuth0IdFromJwtIfAny) {
        Curso c = new Curso();

        // prioridad al JWT; si no hay, usa el body
        String docenteId = (docenteAuth0IdFromJwtIfAny != null && !docenteAuth0IdFromJwtIfAny.isBlank())
                ? docenteAuth0IdFromJwtIfAny
                : dto.getDocenteAuth0Id();

        if (docenteId == null || docenteId.isBlank()) {
            throw new IllegalArgumentException("docenteAuth0Id no provisto");
        }
        c.setDocenteAuth0Id(docenteId);

        if (dto.getNombre() != null)        c.setNombre(dto.getNombre());
        if (dto.getCodigo() != null)        c.setCodigo(dto.getCodigo());

        return cursoRepository.save(c);
    }

    public Curso obtenerPorCodigo(String codigo) {
        return cursoRepository.findByCodigoIgnoreCase(codigo.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado…" + codigo));
    }
}
