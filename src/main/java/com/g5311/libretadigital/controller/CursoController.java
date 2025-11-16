package com.g5311.libretadigital.controller;

import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.g5311.libretadigital.model.dto.CursoDto;
import com.g5311.libretadigital.model.dto.ExamenEstadoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.g5311.libretadigital.model.Curso;
import com.g5311.libretadigital.service.AsistenciaService;
import com.g5311.libretadigital.service.CursoService;
import com.g5311.libretadigital.service.NotaService;

import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private AsistenciaService asistenciaService;
    @Autowired
    private NotaService notasService;


    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @GetMapping("/profesor/{auth0Id}")
    public List<Curso> obtenerCursosPorProfesor(@PathVariable String auth0Id) {
        return cursoService.obtenerCursosPorDocente(auth0Id);
    }

    @GetMapping("/mios")
    public List<Curso> obtenerMisCursos(@AuthenticationPrincipal Jwt jwt) {
        String auth0Id = jwt.getSubject(); // viene como "auth0|xxxx"
        return cursoService.obtenerCursosPorDocente(auth0Id);
    }

    // PROFESOR o BEDEL
    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Curso> obtenerPorCodigo(
            @PathVariable String codigo) {
        return ResponseEntity.ok(cursoService.obtenerPorCodigo(codigo));
    }

    // (A) POST usando el docente del JWT
    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @PostMapping("/todos")
    public ResponseEntity<Curso> crearMiCurso(@AuthenticationPrincipal Jwt jwt,
                                              @RequestBody CursoDto dto) throws ParseException {
        String docenteAuth0Id = jwt.getSubject();
        Curso creado = cursoService.crearCurso(dto, docenteAuth0Id);
        // 201 + Location (si tenés getId en Curso)
        return ResponseEntity
                .created(URI.create("/api/cursos/" + creado.getId()))
                .body(creado);
    }

    // --- NUEVO: listar según rol ---
    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @GetMapping("/todos")
    public ResponseEntity<List<Curso>> listarCursosSegunRol(@AuthenticationPrincipal Jwt jwt) {
        boolean esBedel = tieneRol("ROLE_BEDEL");
        List<Curso> resultado;

        if (esBedel) {
            resultado = cursoService.findAll();
        } else { // PROFESOR
            String docenteAuth0Id = jwt.getSubject(); // sub del token de Auth0
            resultado =cursoService.obtenerCursosPorDocente(docenteAuth0Id);
        }
        return ResponseEntity.ok(resultado);
    }

    // Helper para leer authorities mapeadas por Spring Security
    private boolean tieneRol(String rolEsperado) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(rolEsperado));
    }

    //POST indicando el docente en el body
    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping
    public ResponseEntity<Curso> crearCurso(@RequestBody CursoDto dto) throws ParseException {
        Curso creado = cursoService.crearCurso(dto, null);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/cursos/" + creado.getId()))
                .body(creado);
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('BEDEL')")
    @GetMapping("/busqueda/final")
    public ResponseEntity<Curso> getByCodigoYFecha(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("codigo") String codigoCurso,
            @RequestParam("fecha") String fechaStr) {

        LocalDate fechaBusqueda = LocalDate.parse(fechaStr);

        return cursoService.findByCodigoAndFecha(codigoCurso, fechaBusqueda)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No se encontró curso con código '%s' y fecha '%s'", codigoCurso, fechaBusqueda)));
    }


    @GetMapping("/{examenId}/estado")
public ExamenEstadoDto getEstado(@PathVariable UUID examenId) {

    boolean asistenciaCargada = asistenciaService.existsByCursoId(examenId);
    boolean notasCargadas = notasService.existsByCursoId(examenId);

    return new ExamenEstadoDto(
        examenId,
        asistenciaCargada,
        notasCargadas
    );
}

}
