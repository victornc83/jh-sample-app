package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Impuesto;
import io.github.jhipster.application.service.ImpuestoService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Impuesto.
 */
@RestController
@RequestMapping("/api")
public class ImpuestoResource {

    private final Logger log = LoggerFactory.getLogger(ImpuestoResource.class);

    private static final String ENTITY_NAME = "jhSampleAppImpuesto";

    private final ImpuestoService impuestoService;

    public ImpuestoResource(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    /**
     * POST  /impuestos : Create a new impuesto.
     *
     * @param impuesto the impuesto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new impuesto, or with status 400 (Bad Request) if the impuesto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/impuestos")
    @Timed
    public ResponseEntity<Impuesto> createImpuesto(@Valid @RequestBody Impuesto impuesto) throws URISyntaxException {
        log.debug("REST request to save Impuesto : {}", impuesto);
        if (impuesto.getId() != null) {
            throw new BadRequestAlertException("A new impuesto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Impuesto result = impuestoService.save(impuesto);
        return ResponseEntity.created(new URI("/api/impuestos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /impuestos : Updates an existing impuesto.
     *
     * @param impuesto the impuesto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated impuesto,
     * or with status 400 (Bad Request) if the impuesto is not valid,
     * or with status 500 (Internal Server Error) if the impuesto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/impuestos")
    @Timed
    public ResponseEntity<Impuesto> updateImpuesto(@Valid @RequestBody Impuesto impuesto) throws URISyntaxException {
        log.debug("REST request to update Impuesto : {}", impuesto);
        if (impuesto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Impuesto result = impuestoService.save(impuesto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, impuesto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /impuestos : get all the impuestos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of impuestos in body
     */
    @GetMapping("/impuestos")
    @Timed
    public List<Impuesto> getAllImpuestos() {
        log.debug("REST request to get all Impuestos");
        return impuestoService.findAll();
    }

    /**
     * GET  /impuestos/:id : get the "id" impuesto.
     *
     * @param id the id of the impuesto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the impuesto, or with status 404 (Not Found)
     */
    @GetMapping("/impuestos/{id}")
    @Timed
    public ResponseEntity<Impuesto> getImpuesto(@PathVariable Long id) {
        log.debug("REST request to get Impuesto : {}", id);
        Optional<Impuesto> impuesto = impuestoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impuesto);
    }

    /**
     * DELETE  /impuestos/:id : delete the "id" impuesto.
     *
     * @param id the id of the impuesto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/impuestos/{id}")
    @Timed
    public ResponseEntity<Void> deleteImpuesto(@PathVariable Long id) {
        log.debug("REST request to delete Impuesto : {}", id);
        impuestoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
