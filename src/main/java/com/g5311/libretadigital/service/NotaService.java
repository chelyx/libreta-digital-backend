package com.g5311.libretadigital.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.User;
import com.g5311.libretadigital.model.dto.NotaBulkDto;
import com.g5311.libretadigital.model.dto.NotaDto;
import com.g5311.libretadigital.model.dto.NotaResponse;
import com.g5311.libretadigital.repository.CursoRepository;
import com.g5311.libretadigital.repository.NotaRepository;
import com.g5311.libretadigital.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotaService {
    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EmailService emailService;

    public Nota guardarNota(UUID cursoId, String alumnoAuth0Id, String descripcion, Double valor) {
        Nota nota = new Nota();
        nota.setCursoId(cursoId);
        nota.setAlumnoAuth0Id(alumnoAuth0Id);
        nota.setDescripcion(descripcion);
        nota.setValor(valor);

        return notaRepository.save(nota);
    }

    public Nota updateNota(UUID id, Double valor) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con id: " + id));
        nota.setValor(valor);
        return notaRepository.save(nota);
    }

    public List<Nota> updateNotasBulk(NotaBulkDto notasBulkDto) {
        List<Nota> notasExistentes = notasBulkDto.getNotas().stream()
                .map(n -> {
                    Nota existing = notaRepository.findById(n.getId())
                            .orElseThrow(() -> new RuntimeException("Nota no encontrada con id: " + n.getId()));
                    existing.setValor(n.getValor());
                    return existing;
                })
                .collect(Collectors.toList());

        return notaRepository.saveAll(notasExistentes);
    }

    public List<NotaResponse> obtenerNotasDeCurso(UUID cursoId) {
        return notaRepository.findNotaResponsesByCursoId(cursoId);
    }

    public boolean existsByCursoId(UUID cursoId) {
        return notaRepository.existsByCursoId(cursoId);
    }

    public void enviarNotasPorMailCurso(List<Nota> notasData) {
        for (Nota data : notasData) {
            if (data.isPresente()) {
                User alumno = userRepository.findById(data.getAlumnoAuth0Id()).orElse(null);
                Curso curso = cursoRepository.findById(data.getCursoId()).orElse(null);
                try {
                    emailService.enviarNotaPorMail(
                            alumno.getNombre(),
                            data,
                            alumno.getEmail(),
                            curso.getNombre());
                } catch (Exception e) {
                    System.out.println("Error enviando email a " + alumno.getEmail() + ": " + e.getMessage());
                }
            }
        }
    }

    public List<Nota> guardarNotasEnBulk(UUID cursoId, List<NotaDto> notas) {

        List<Nota> notasAGuardar = new ArrayList<>();
        for (NotaDto data : notas) {
            Nota n = notaRepository.findByCursoIdAndAlumnoAuth0Id(cursoId, data.getAlumnoId())
                    .orElse(new Nota());
            n.setCursoId(cursoId);
            n.setAlumnoAuth0Id(data.getAlumnoId());
            n.setDescripcion((String) data.getDescripcion());
            if (data.getValor() != null) {
                n.setValor(data.getValor());
                n.setPresente(true);
            } else {
                n.setValor(null);
                n.setPresente(false);
            }
            notasAGuardar.add(n);
        }

        return notaRepository.saveAll(notasAGuardar);
    }

    public List<NotaResponse> obtenerNotasPorAlumno(String alumnoAuth0Id) {
        return notaRepository.findNotaResponseByAlumnoId(alumnoAuth0Id);
    }

    public List<Nota> findNotasParaSellar(){
        LocalDate limite = LocalDate.now();//.minusDays(1);
        return notaRepository.findNotasParaSellar(limite);
    }

    public List<Nota> findAllById(List<UUID> notas){
       
        return notaRepository.findAllByIdIn(notas);
    }

}
