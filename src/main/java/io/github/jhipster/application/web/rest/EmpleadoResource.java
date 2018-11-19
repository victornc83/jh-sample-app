package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Empleado;
import io.github.jhipster.application.repository.EmpleadoRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Empleado.
 */
@RestController
@RequestMapping("/api")
public class EmpleadoResource {

    private final Logger log = LoggerFactory.getLogger(EmpleadoResource.class);

    private static final String ENTITY_NAME = "jhSampleAppEmpleado";

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoResource(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    /**
     * POST  /empleados : Create a new empleado.
     *
     * @param empleado the empleado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empleado, or with status 400 (Bad Request) if the empleado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/empleados")
    @Timed
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) throws URISyntaxException {
        log.debug("REST request to save Empleado : {}", empleado);
        if (empleado.getId() != null) {
            throw new BadRequestAlertException("A new empleado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Empleado result = empleadoRepository.save(empleado);
        return ResponseEntity.created(new URI("/api/empleados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /empleados : Updates an existing empleado.
     *
     * @param empleado the empleado to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empleado,
     * or with status 400 (Bad Request) if the empleado is not valid,
     * or with status 500 (Internal Server Error) if the empleado couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/empleados")
    @Timed
    public ResponseEntity<Empleado> updateEmpleado(@RequestBody Empleado empleado) throws URISyntaxException {
        log.debug("REST request to update Empleado : {}", empleado);
        if (empleado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Empleado result = empleadoRepository.save(empleado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empleado.getId().toString()))
            .body(result);
    }

    /**
     * GET  /empleados : get all the empleados.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of empleados in body
     */
    @GetMapping("/empleados")
    @Timed
    public ResponseEntity<List<Empleado>> getAllEmpleados(Pageable pageable) {
        log.debug("REST request to get a page of Empleados");
        Page<Empleado> page = empleadoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/empleados");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /empleados/:id : get the "id" empleado.
     *
     * @param id the id of the empleado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empleado, or with status 404 (Not Found)
     */
    @GetMapping("/empleados/{id}")
    @Timed
    public ResponseEntity<Empleado> getEmpleado(@PathVariable Long id) {
        log.debug("REST request to get Empleado : {}", id);
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(empleado);
    }

    /**
     * DELETE  /empleados/:id : delete the "id" empleado.
     *
     * @param id the id of the empleado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/empleados/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        log.debug("REST request to delete Empleado : {}", id);

        empleadoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
