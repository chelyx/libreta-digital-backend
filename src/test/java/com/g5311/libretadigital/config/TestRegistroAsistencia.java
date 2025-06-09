package com.g5311.libretadigital.config;

import com.g5311.libretadigital.model.Alumno;
import com.g5311.libretadigital.model.AlumnoAula;
import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.Aula;
import com.g5311.libretadigital.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRegistroAsistencia {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AlumnoAulaRepository alumnoAulaRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private NotaRepository notaRepository;

    private UUID alumnoAulaId;

    @BeforeEach
    public void setup() {

        notaRepository.deleteAll();
        alumnoAulaRepository.deleteAll();
        asistenciaRepository.deleteAll();
        aulaRepository.deleteAll();
        alumnoRepository.deleteAll();

        Alumno alumno = new Alumno("Lucía", "Fernández", "lucia@mail.com", "LEG001");
        alumno = alumnoRepository.save(alumno);

        Aula aula = new Aula();
        aula.setCodigo("4B");
        aula.setCuatri("2");
        aula.setAnio(2024);
        aula = aulaRepository.save(aula);

        AlumnoAula relacion = new AlumnoAula();
        relacion.setAlumno(alumno);
        relacion.setAula(aula);
        relacion = alumnoAulaRepository.save(relacion);

        alumnoAulaId = relacion.getId();
    }

    @Test
    public void testRegistrarAsistencia() throws Exception {
        String json = """
        {
            "fecha": "2025-06-09",
            "asistencias": {
                "%s": true
            }
        }
        """.formatted(alumnoAulaId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/asistencia/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Verificamos que la asistencia fue guardada
        List<Asistencia> asistencias = asistenciaRepository.findAll();
        assertEquals(1, asistencias.size());
        assertEquals("Presente", asistencias.get(0).getEstado());
        assertEquals("2025-06-09", asistencias.get(0).getFecha());
    }
}
