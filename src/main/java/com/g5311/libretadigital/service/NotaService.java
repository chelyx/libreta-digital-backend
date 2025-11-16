package com.g5311.libretadigital.service;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.dto.NotaBFA;
import com.g5311.libretadigital.model.dto.NotaBulkDto;
import com.g5311.libretadigital.model.dto.NotaResponse;
import com.g5311.libretadigital.repository.NotaRepository;

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
    private TsaService tsaService;

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

    // public List<NotaResponse> obtenerNotasDeAlumnoEnCurso(UUID cursoId, String
    // alumnoAuth0Id) {

    // return notaRepository.findByCursoIdAndAlumnoAuth0Id(cursoId, alumnoAuth0Id);
    // }

    public List<Nota> guardarNotasEnBulk(UUID cursoId, List<Nota> notas) {
        List<Nota> notasAGuardar = notas.stream()
                .map(n -> {
                    n.setCursoId(cursoId);
                    return n;
                })
                .collect(Collectors.toList());

        return notaRepository.saveAll(notasAGuardar);
    }

    public void registrarNotaTSA(NotaBFA nota) {
        // try {
        // // 1️⃣ Generar hash (sin exponer datos personales)
        // String rutaPdf = "nota_" + nota.getLegajoAlumno() + ".pdf";
        // PdfGenerator.generarPdfNota(rutaPdf, nota.getFecha().toString(),
        // nota.getLegajoAlumno(),
        // nota.getMateria(), nota.getNota());
        // String hash = HashUtil.sha256(rutaPdf);

        // // 2️⃣ Enviar a TSA de BFA
        // var result = tsaService.registrarHashEnTsa(hash);

        // // 3️⃣ Guardar en tu base de datos el hash y los datos devueltos
        // System.out.println("Nota registrada en la blockchain BFA:");
        // System.out.println(result);
        // } catch (Exception e) {
        // throw new RuntimeException("Error al registrar la nota en BFA TSA", e);
        // }
    }

    public List<NotaResponse> obtenerNotasPorAlumno(String alumnoAuth0Id) {
        return notaRepository.findNotaResponseByAlumnoId(alumnoAuth0Id);
    }
}
