package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.repository.CursoRepository;
import com.g5311.libretadigital.repository.NotaRepository;
import com.g5311.libretadigital.utils.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotaService {
    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private TsaService tsaService;

    public Nota guardarNota(UUID cursoId, String alumnoAuth0Id, String descripcion, Double valor) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        Nota nota = new Nota();
        nota.setCurso(curso);
        nota.setAlumnoAuth0Id(alumnoAuth0Id);
        nota.setDescripcion(descripcion);
        nota.setValor(valor);

        return notaRepository.save(nota);
    }

    public List<Nota> obtenerNotasDeCurso(UUID cursoId) {
        return notaRepository.findByCursoId(cursoId);
    }

    public List<Nota> obtenerNotasDeAlumnoEnCurso(UUID cursoId, String alumnoAuth0Id) {
        return notaRepository.findByCursoIdAndAlumnoAuth0Id(cursoId, alumnoAuth0Id);
    }

    public List<Nota> guardarNotasEnBulk(UUID cursoId, List<Nota> notas) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        List<Nota> notasAGuardar = notas.stream()
                .map(n -> {
                    n.setCurso(curso);
                    return n;
                })
                .collect(Collectors.toList());

        return notaRepository.saveAll(notasAGuardar);
    }

    public void registrarNotaTSA(NotaDto nota) {
        try {
            // 1️⃣ Generar hash (sin exponer datos personales)
            String hash = HashUtil.generarHash(nota);

            // 2️⃣ Enviar a TSA de BFA
            var result = tsaService.registrarHashEnTsa(hash);

            // 3️⃣ Guardar en tu base de datos el hash y los datos devueltos
            System.out.println("Nota registrada en la blockchain BFA:");
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar la nota en BFA TSA", e);
        }
    }

    public List<Nota> obtenerNotasPorAlumno(String alumnoAuth0Id) {
        return notaRepository.findByAlumnoAuth0Id(alumnoAuth0Id);
    }
}
