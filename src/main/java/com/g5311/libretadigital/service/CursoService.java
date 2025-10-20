package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.repository.CursoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> obtenerCursosPorDocente(String docenteAuth0Id) {
        return cursoRepository.findByDocenteAuth0Id(docenteAuth0Id);
    }
}
