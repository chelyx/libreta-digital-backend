package com.g5311.libretadigital.config;

import com.g5311.libretadigital.model.Alumno;
import com.g5311.libretadigital.model.AlumnoAula;
import com.g5311.libretadigital.model.Aula;
import com.g5311.libretadigital.model.dto.AlumnoDto;
import com.g5311.libretadigital.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;


@SpringBootTest
@AutoConfigureMockMvc
public class TestTomaDeAsistencia {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AulaRepository aulaRepository;

    /*@Autowired
    private PersonaRepository alumnoRepository;*/
    @Autowired
    private AlumnoRepository alumnoRepository;


    @Autowired
    private AlumnoAulaRepository alumnoAulaRepository;

    @Autowired
    private NotaRepository notaRepository;

    @BeforeEach
    public void setup() {

        /*notaRepository.deleteAll();
        alumnoAulaRepository.deleteAll();
        aulaRepository.deleteAll();
        alumnoRepository.deleteAll();*/


        Aula aula = new Aula();
        aula.setCodigo("3A");
        aula.setCuatri("1");
        aula.setAnio(2024);
        aula = aulaRepository.save(aula);

        Alumno alumno = (new Alumno("Juan", "Pérez", "juan@mail.com", null));
        alumno = alumnoRepository.save(alumno);



        AlumnoAula relacion = new AlumnoAula();
        relacion.setAula(aula);
        relacion.setAlumno(alumno);
        alumnoAulaRepository.save(relacion);
    }

    @Test
    public void testListarAlumnosPorAula() throws Exception {
        // Buscamos el ID del aula que insertamos
        Aula aula = aulaRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/" + aula.getId() + "/alumnos"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].apellido").value("Perez"));
    }
}
// Este es el ENDOPINT .... GET /api/{id}/alumnos
