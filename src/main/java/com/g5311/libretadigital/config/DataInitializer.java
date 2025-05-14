package com.g5311.libretadigital.config;

import java.util.List;

import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.repository.NotaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.g5311.libretadigital.model.Alumno;
import com.g5311.libretadigital.model.Materia;
import com.g5311.libretadigital.model.Profesor;
import com.g5311.libretadigital.repository.MateriaRepository;
import com.g5311.libretadigital.repository.PersonaRepository;

@Component
public class DataInitializer implements CommandLineRunner {

        private final PersonaRepository personaRepository;
        private final MateriaRepository materiaRepository;

        private final NotaRepository notaRepository;

        public DataInitializer(PersonaRepository personaRepository, MateriaRepository materiaRepository,
                        NotaRepository notaRepository) {
                this.personaRepository = personaRepository;
                this.materiaRepository = materiaRepository;
                this.notaRepository = notaRepository;
        }

        @Override
        public void run(String... args) {

                // Limpiar
                // personaRepository.deleteAll();
                // materiaRepository.deleteAll();
                // notaRepository.deleteAll();

                // Insertar ALUMNOS
                Alumno juan = new Alumno(
                                "Juan",
                                "Perez",
                                "juan.perez@mail.com",
                                "a92bbec7");

                personaRepository.saveAll(List.of(
                                juan,
                                new Alumno(
                                                "Ana",
                                                "Gomez",
                                                "ana.gomez@mail.com",
                                                "5b3d2c68"),
                                new Alumno(
                                                "Luis",
                                                "Martinez",
                                                "luis.martinez@mail.com",
                                                "90e1d037"),
                                new Alumno(
                                                "Maria",
                                                "Lopez",
                                                "maria.lopez@mail.com",
                                                "7bce4e35"),
                                new Alumno(
                                                "Sofia",
                                                "Fernandez",
                                                "sofia.fernandez@mail.com",
                                                "dc6f4f7d")));

                // Insertar PROFESORES
                Profesor carlos = new Profesor(
                                "Carlos",
                                "Suarez",
                                "carlos.suarez@mail.com",
                                "Proyecto Final");
                personaRepository.saveAll(List.of(
                                carlos,
                                new Profesor(
                                                "Laura",
                                                "Vega",
                                                "laura.vega@mail.com",
                                                "Proyecto Final"),
                                new Profesor(
                                                "Diego",
                                                "Castro",
                                                "diego.castro@mail.com",
                                                "Proyecto Final"),
                                new Profesor(
                                                "Paula",
                                                "Ramos",
                                                "paula.ramos@mail.com",
                                                "Proyecto Final"),
                                new Profesor(
                                                "Miguel",
                                                "Romero",
                                                "miguel.romero@mail.com",
                                                "Proyecto Final")));

                // Insertar MATERIAS

                Materia pdep = new Materia(
                                "Paradigmas de Programacion",
                                "K2040");
                materiaRepository.saveAll(List.of(
                                pdep,
                                new Materia(
                                                "Estructura de Datos",
                                                "K2041"),
                                new Materia(

                                                "Diseño de Sistemas",
                                                "K3042")));

                // Insertar NOTAS
                notaRepository.save(new Nota("13052025", juan, carlos, pdep, 9));

        }
}
