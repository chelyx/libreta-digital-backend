package com.g5311.libretadigital.config;

import java.util.List;

import com.g5311.libretadigital.model.*;
import com.g5311.libretadigital.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

        private final PersonaRepository personaRepository;
        private final MateriaRepository materiaRepository;

        private final NotaRepository notaRepository;

        private final AulaRepository aulaRepository;
        private final AsistenciaRepository asistenciaRepository;
        private final AlumnoAulaRepository alumnoAulaRepository;

        public DataInitializer(PersonaRepository personaRepository, MateriaRepository materiaRepository,
                        NotaRepository notaRepository, AulaRepository aulaRepository,
                        AsistenciaRepository asistenciaRepository, AlumnoAulaRepository alumnoAulaRepository) {
                this.personaRepository = personaRepository;
                this.materiaRepository = materiaRepository;
                this.notaRepository = notaRepository;
                this.aulaRepository = aulaRepository;
                this.asistenciaRepository = asistenciaRepository;
                this.alumnoAulaRepository = alumnoAulaRepository;
        }

        @Override
        public void run(String... args) {

                // Insertar MATERIAS

                // Materia pdep = new Materia(
                // "Paradigmas de Programacion",
                // "K2040");
                // materiaRepository.saveAll(List.of(
                // pdep,
                // new Materia(
                // "Estructura de Datos",
                // "K2041"),
                // new Materia(

                // "Diseño de Sistemas",
                // "K3042")));

                // Insertar NOTAS
                // notaRepository.save(new Nota("13052025", juan, carlos, pdep, 9));

                // INSERTAR AULA

                // Aula aula = new Aula(pdep, 2025, "K2025", "1C", carlos);
                // aulaRepository.saveAll(List.of(aula));
                // INSERTAR ALUMNO AULA

                // AlumnoAula relacion = new AlumnoAula();
                // relacion.setAlumno(juan);
                // relacion.setAula(aula);
                // alumnoAulaRepository.save(relacion);

                // INSERTAR ASISTENCIA
                // Asistencia asistencia = new Asistencia();
                // asistencia.setFecha("15042025");
                // asistencia.setEstado("PRESENTE");

                // // Relación ManyToMany desde AlumnoAula
                // relacion.getAsistencias().add(asistencia);
                // asistencia.getAlumnoAulas().add(relacion);

                // asistenciaRepository.save(asistencia);
                // alumnoAulaRepository.save(relacion);

        }
}
