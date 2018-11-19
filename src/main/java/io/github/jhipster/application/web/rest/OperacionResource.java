package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Operacion;
import io.github.jhipster.application.service.OperacionService;
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
 * REST controller for managing Operacion.
 */
@RestController
@RequestMapping("/api")
public class OperacionResource {

    private final Logger log = LoggerFactory.getLogger(OperacionResource.class);

    private static final String ENTITY_NAME = "jhSampleAppOperacion";

    private final OperacionService operacionService;

    public OperacionResource(OperacionService operacionService) {
        this.operacionService = operacionService;
    }

    /**
     * POST  /operacions : Create a new operacion.
     *
     * @param operacion the operacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operacion, or with status 400 (Bad Request) if the operacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operacions")
    @Timed
    public ResponseEntity<Operacion> createOperacion(@RequestBody Operacion operacion) throws URISyntaxException {
        log.debug("REST request to save Operacion : {}", operacion);
        if (operacion.getId() != null) {
            throw new BadRequestAlertException("A new operacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Operacion result = operacionService.save(operacion);
        return ResponseEntity.created(new URI("/api/operacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operacions : Updates an existing operacion.
     *
     * @param operacion the operacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operacion,
     * or with status 400 (Bad Request) if the operacion is not valid,
     * or with status 500 (Internal Server Error) if the operacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operacions")
    @Timed
    public ResponseEntity<Operacion> updateOperacion(@RequestBody Operacion operacion) throws URISyntaxException {
        log.debug("REST request to update Operacion : {}", operacion);
        if (operacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Operacion result = operacionService.save(operacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operacions : get all the operacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operacions in body
     */
    @GetMapping("/operacions")
    @Timed
    public ResponseEntity<List<Operacion>> getAllOperacions(Pageable pageable) {
        log.debug("REST request to get a page of Operacions");
        Page<Operacion> page = operacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operacions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /operacions/:id : get the "id" operacion.
     *
     * @param id the id of the operacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operacion, or with status 404 (Not Found)
     */
    @GetMapping("/operacions/{id}")
    @Timed
    public ResponseEntity<Operacion> getOperacion(@PathVariable Long id) {
        log.debug("REST request to get Operacion : {}", id);
        Optional<Operacion> operacion = operacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operacion);
    }

    /**
     * DELETE  /operacions/:id : delete the "id" operacion.
     *
     * @param id the id of the operacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperacion(@PathVariable Long id) {
        log.debug("REST request to delete Operacion : {}", id);
        operacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
