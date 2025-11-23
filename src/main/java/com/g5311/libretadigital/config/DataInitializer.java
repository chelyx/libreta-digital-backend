package com.g5311.libretadigital.config;

import com.g5311.libretadigital.model.Asistencia;
import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.model.Nota;
import com.g5311.libretadigital.model.User;
import com.g5311.libretadigital.repository.AsistenciaRepository;
import com.g5311.libretadigital.repository.CursoRepository;
import com.g5311.libretadigital.repository.NotaRepository;
import com.g5311.libretadigital.repository.UserRepository;
import com.g5311.libretadigital.service.Auth0Service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataInitializer {

        private final CursoRepository cursoRepository;
        private final UserRepository userRepository;
        private final AsistenciaRepository asistenciaRepository;
        private final NotaRepository notaRepository;

        @Autowired
        private Auth0Service auth0Service;

        public DataInitializer(CursoRepository cursoRepository,
                        UserRepository userRepository,
                        NotaRepository notaRepository,
                        AsistenciaRepository asistenciaRepository) {
                this.cursoRepository = cursoRepository;
                this.userRepository = userRepository;
                this.notaRepository = notaRepository;
                this.asistenciaRepository = asistenciaRepository;
        }

        @PostConstruct
        public void init() {
                // Sincronizamos primero los usuarios reales de Auth0
                auth0Service.syncUsersFromAuth0();

                // Si ya hay cursos cargados, no volvemos a inicializar nada
                /*
                 * if (cursoRepository.count() > 0) {
                 * System.out.println("➡️ Datos iniciales ya cargados, se omite inicialización."
                 * );
                 * return;
                 * }
                 */

                // 👩‍🏫 Profesores (los mismos que ya venías usando)
                User prof1 = userRepository.findById("auth0|68bc93d0f21d782f04f36998").orElse(null);
                User andrea = userRepository.findById("google-oauth2|117509478967819287002").orElse(null);

                // 👨‍🎓 Alumnos DEMO (incluye los 5 nombres del grupo)
                User yasmin = userRepository.findById("google-oauth2|110308643967461901799").orElse(null);
                yasmin.setLegajo("168744-0");

                User ezequiel = userRepository.findById("google-oauth2|110168900827455010756").orElse(null);
                ezequiel.setLegajo("167963-6");

                User cecilia = userRepository.findById("google-oauth2|109619556506259057184").orElse(null);
                cecilia.setLegajo("164428-2");

                User maximiliano = userRepository.findById("google-oauth2|116911911318829576526").orElse(null);
                maximiliano.setLegajo("168551-0");

                // Alumnos extra para llegar a ~20 por curso
                User a1 = new User();
                a1.setAuth0Id("auth0|alum_demo01");
                a1.setNombre("Juan Gómez");
                a1.setEmail("juan.gomez@frba.utn.edu.ar");
                a1.setRol("ALUMNO");
                a1.setLegajo("1700001");

                User a2 = new User();
                a2.setAuth0Id("auth0|alum_demo02");
                a2.setNombre("María López");
                a2.setEmail("maria.lopez@frba.utn.edu.ar");
                a2.setRol("ALUMNO");
                a2.setLegajo("1700002");

                User a3 = new User();
                a3.setAuth0Id("auth0|alum_demo03");
                a3.setNombre("Sofía Martínez");
                a3.setEmail("sofia.martinez@frba.utn.edu.ar");
                a3.setRol("ALUMNO");
                a3.setLegajo("1700003");

                User a4 = new User();
                a4.setAuth0Id("auth0|alum_demo04");
                a4.setNombre("Lucas Fernández");
                a4.setEmail("lucas.fernandez@frba.utn.edu.ar");
                a4.setRol("ALUMNO");
                a4.setLegajo("1700004");

                User a5 = new User();
                a5.setAuth0Id("auth0|alum_demo05");
                a5.setNombre("Carolina Díaz");
                a5.setEmail("carolina.diaz@frba.utn.edu.ar");
                a5.setRol("ALUMNO");
                a5.setLegajo("1700005");

                User a6 = new User();
                a6.setAuth0Id("auth0|alum_demo06");
                a6.setNombre("Martín Pérez");
                a6.setEmail("martin.perez@frba.utn.edu.ar");
                a6.setRol("ALUMNO");
                a6.setLegajo("1700006");

                User a7 = new User();
                a7.setAuth0Id("auth0|alum_demo07");
                a7.setNombre("Laura Sánchez");
                a7.setEmail("laura.sanchez@frba.utn.edu.ar");
                a7.setRol("ALUMNO");
                a7.setLegajo("1700007");

                User a8 = new User();
                a8.setAuth0Id("auth0|alum_demo08");
                a8.setNombre("Diego Romero");
                a8.setEmail("diego.romero@frba.utn.edu.ar");
                a8.setRol("ALUMNO");
                a8.setLegajo("1700008");

                User a9 = new User();
                a9.setAuth0Id("auth0|alum_demo09");
                a9.setNombre("Valentina Herrera");
                a9.setEmail("valentina.herrera@frba.utn.edu.ar");
                a9.setRol("ALUMNO");
                a9.setLegajo("1700009");

                User a10 = new User();
                a10.setAuth0Id("auth0|alum_demo10");
                a10.setNombre("Nicolás Ruiz");
                a10.setEmail("nicolas.ruiz@frba.utn.edu.ar");
                a10.setRol("ALUMNO");
                a10.setLegajo("1700010");

                User a11 = new User();
                a11.setAuth0Id("auth0|alum_demo11");
                a11.setNombre("Camila Torres");
                a11.setEmail("camila.torres@frba.utn.edu.ar");
                a11.setRol("ALUMNO");
                a11.setLegajo("1700011");

                User a12 = new User();
                a12.setAuth0Id("auth0|alum_demo12");
                a12.setNombre("Federico Álvarez");
                a12.setEmail("federico.alvarez@frba.utn.edu.ar");
                a12.setRol("ALUMNO");
                a12.setLegajo("1700012");

                User a13 = new User();
                a13.setAuth0Id("auth0|alum_demo13");
                a13.setNombre("Julieta Ríos");
                a13.setEmail("julieta.rios@frba.utn.edu.ar");
                a13.setRol("ALUMNO");
                a13.setLegajo("1700013");

                User a14 = new User();
                a14.setAuth0Id("auth0|alum_demo14");
                a14.setNombre("Agustín Blanco");
                a14.setEmail("agustin.blanco@frba.utn.edu.ar");
                a14.setRol("ALUMNO");
                a14.setLegajo("1700014");

                User a15 = new User();
                a15.setAuth0Id("auth0|alum_demo15");
                a15.setNombre("Rocío Castro");
                a15.setEmail("rocio.castro@frba.utn.edu.ar");
                a15.setRol("ALUMNO");
                a15.setLegajo("1700015");

                User araceli = userRepository.findById("google-oauth2|117672822587731017632").orElse(null);

                List<User> alumnosDemo = List.of(
                                yasmin, araceli, ezequiel, cecilia, maximiliano,
                                a1, a2, a3, a4, a5,
                                a6, a7, a8, a9, a10,
                                a11, a12, a13, a14, a15);

                userRepository.saveAll(alumnosDemo);

                // Fechas de ejemplo
                LocalDate hoy = LocalDate.now();

                // Usamos el mismo set de ~20 alumnos para todos los cursos
                Set<User> alumnosSet = new HashSet<>(alumnosDemo);

                // 📘 Cursos (uno por materia / códigos K3023+)
                Curso c1 = new Curso();
                c1.setNombre("Sistemas de Gestión");
                c1.setCodigo("K3023");
                c1.setDocenteAuth0Id(prof1 != null ? prof1.getAuth0Id() : null);
                c1.setFecha(hoy.minusDays(30));
                c1.setEsFinal(false);
                c1.setAlumnos(alumnosSet);

                Curso c2 = new Curso();
                c2.setNombre("Inteligencia Artificial");
                c2.setCodigo("K3024");
                c2.setDocenteAuth0Id(andrea != null ? andrea.getAuth0Id() : null);
                c2.setFecha(hoy.minusDays(25));
                c2.setEsFinal(false);
                c2.setAlumnos(alumnosSet);

                Curso c3 = new Curso();
                c3.setNombre("Administración Gerencial");
                c3.setCodigo("K3025");
                c3.setDocenteAuth0Id(prof1 != null ? prof1.getAuth0Id() : null);
                c3.setFecha(hoy.minusDays(20));
                c3.setEsFinal(false);
                c3.setAlumnos(alumnosSet);

                Curso c4 = new Curso();
                c4.setNombre("Gestión de la Transformación Organizacional");
                c4.setCodigo("K3026");
                c4.setDocenteAuth0Id(andrea != null ? andrea.getAuth0Id() : null);
                c4.setFecha(hoy.minusDays(15));
                c4.setEsFinal(false);
                c4.setAlumnos(alumnosSet);

                Curso c5 = new Curso();
                c5.setNombre("Legislación");
                c5.setCodigo("K3027");
                c5.setDocenteAuth0Id(prof1 != null ? prof1.getAuth0Id() : null);
                c5.setFecha(hoy.minusDays(10));
                c5.setEsFinal(false);
                c5.setAlumnos(alumnosSet);

                Curso c6 = new Curso();
                c6.setNombre("Simulación");
                c6.setCodigo("K3028");
                c6.setDocenteAuth0Id(andrea != null ? andrea.getAuth0Id() : null);
                c6.setFecha(hoy.minusDays(5));
                c6.setEsFinal(false);
                c6.setAlumnos(alumnosSet);

                Curso c7 = new Curso();
                c7.setNombre("Ingeniería de Software");
                c7.setCodigo("K3029");
                c7.setDocenteAuth0Id(prof1 != null ? prof1.getAuth0Id() : null);
                c7.setFecha(hoy);
                c7.setEsFinal(true);
                c7.setAlumnos(alumnosSet);

                cursoRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6, c7));

                // ✅ En este punto los cursos ya tienen ID asignado
                List<Curso> cursos = List.of(c1, c2, c3, c4, c5, c6, c7);

                // ============================
                // 📌 Notas de ejemplo (NotaDto-like)
                // ============================
                List<Nota> notas = new ArrayList<>();

                for (Curso curso : cursos) {
                        int i = 0;
                        for (User alumno : alumnosDemo) {
                                Nota n = new Nota();
                                // campos equivalente a NotaBulkDto.cursoId + NotaDto
                                n.setCursoId(curso.getId());
                                n.setAlumnoAuth0Id(alumno.getAuth0Id());
                                n.setFecha(curso.getFecha());
                                // valores tipo 6–10 para que quede prolijo
                                double valor = 6.0 + (int) (Math.random() * 4); // si usamos el i tiene siempre la misma
                                                                                // nota el alumno
                                n.setValor(valor);
                                n.setDescripcion("Final");
                                n.setPresente(true);
                                notas.add(n);
                                i++;
                        }
                }

                notaRepository.saveAll(notas);

                // ============================
                // 📌 Asistencias de ejemplo (AsistenciaAlumnoDto-like)
                // ============================
                List<Asistencia> asistencias = new ArrayList<>();
                LocalDate base = hoy.minusDays(3);
                List<LocalDate> fechasAsistencia = List.of(base, base.plusDays(1), base.plusDays(2));

                List<Curso> cursosNoFinales = cursos.stream()
                                .filter(c -> !c.getEsFinal())
                                .toList();
                // se supone que los finales no tienen mil asistencias
                for (Curso curso : cursosNoFinales) {
                        for (LocalDate fecha : fechasAsistencia) {
                                int i = 0;
                                for (User alumno : alumnosDemo) {
                                        Asistencia asis = new Asistencia();
                                        // equivalente a AsistenciaBulkRequest.cursoId + AsistenciaAlumnoDto
                                        asis.setCursoId(curso.getId());
                                        asis.setAlumnoId(alumno.getAuth0Id()); // se mapea contra User.auth0Id
                                        asis.setFecha(fecha);
                                        // patrón simple para mezclar presentes/ausentes
                                        boolean presente = ((i + fecha.getDayOfMonth()) % 4) != 0;
                                        asis.setPresente(presente);
                                        asistencias.add(asis);
                                        i++;
                                }
                        }
                }

                asistenciaRepository.saveAll(asistencias);

                System.out.println("✅ Datos iniciales de alumnos, cursos, notas y asistencias cargados correctamente");
        }

}