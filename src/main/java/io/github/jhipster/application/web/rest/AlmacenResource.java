package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Almacen;
import io.github.jhipster.application.service.AlmacenService;
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
 * REST controller for managing Almacen.
 */
@RestController
@RequestMapping("/api")
public class AlmacenResource {

    private final Logger log = LoggerFactory.getLogger(AlmacenResource.class);

    private static final String ENTITY_NAME = "jhSampleAppAlmacen";

    private final AlmacenService almacenService;

    public AlmacenResource(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    /**
     * POST  /almacens : Create a new almacen.
     *
     * @param almacen the almacen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new almacen, or with status 400 (Bad Request) if the almacen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/almacens")
    @Timed
    public ResponseEntity<Almacen> createAlmacen(@Valid @RequestBody Almacen almacen) throws URISyntaxException {
        log.debug("REST request to save Almacen : {}", almacen);
        if (almacen.getId() != null) {
            throw new BadRequestAlertException("A new almacen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Almacen result = almacenService.save(almacen);
        return ResponseEntity.created(new URI("/api/almacens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almacens : Updates an existing almacen.
     *
     * @param almacen the almacen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated almacen,
     * or with status 400 (Bad Request) if the almacen is not valid,
     * or with status 500 (Internal Server Error) if the almacen couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/almacens")
    @Timed
    public ResponseEntity<Almacen> updateAlmacen(@Valid @RequestBody Almacen almacen) throws URISyntaxException {
        log.debug("REST request to update Almacen : {}", almacen);
        if (almacen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Almacen result = almacenService.save(almacen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, almacen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almacens : get all the almacens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of almacens in body
     */
    @GetMapping("/almacens")
    @Timed
    public List<Almacen> getAllAlmacens() {
        log.debug("REST request to get all Almacens");
        return almacenService.findAll();
    }

    /**
     * GET  /almacens/:id : get the "id" almacen.
     *
     * @param id the id of the almacen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the almacen, or with status 404 (Not Found)
     */
    @GetMapping("/almacens/{id}")
    @Timed
    public ResponseEntity<Almacen> getAlmacen(@PathVariable Long id) {
        log.debug("REST request to get Almacen : {}", id);
        Optional<Almacen> almacen = almacenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(almacen);
    }

    /**
     * DELETE  /almacens/:id : delete the "id" almacen.
     *
     * @param id the id of the almacen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/almacens/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlmacen(@PathVariable Long id) {
        log.debug("REST request to delete Almacen : {}", id);
        almacenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
