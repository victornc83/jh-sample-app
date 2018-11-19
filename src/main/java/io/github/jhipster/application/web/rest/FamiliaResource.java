package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Familia;
import io.github.jhipster.application.service.FamiliaService;
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
 * REST controller for managing Familia.
 */
@RestController
@RequestMapping("/api")
public class FamiliaResource {

    private final Logger log = LoggerFactory.getLogger(FamiliaResource.class);

    private static final String ENTITY_NAME = "jhSampleAppFamilia";

    private final FamiliaService familiaService;

    public FamiliaResource(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    /**
     * POST  /familias : Create a new familia.
     *
     * @param familia the familia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new familia, or with status 400 (Bad Request) if the familia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/familias")
    @Timed
    public ResponseEntity<Familia> createFamilia(@Valid @RequestBody Familia familia) throws URISyntaxException {
        log.debug("REST request to save Familia : {}", familia);
        if (familia.getId() != null) {
            throw new BadRequestAlertException("A new familia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Familia result = familiaService.save(familia);
        return ResponseEntity.created(new URI("/api/familias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /familias : Updates an existing familia.
     *
     * @param familia the familia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated familia,
     * or with status 400 (Bad Request) if the familia is not valid,
     * or with status 500 (Internal Server Error) if the familia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/familias")
    @Timed
    public ResponseEntity<Familia> updateFamilia(@Valid @RequestBody Familia familia) throws URISyntaxException {
        log.debug("REST request to update Familia : {}", familia);
        if (familia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Familia result = familiaService.save(familia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, familia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /familias : get all the familias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of familias in body
     */
    @GetMapping("/familias")
    @Timed
    public List<Familia> getAllFamilias() {
        log.debug("REST request to get all Familias");
        return familiaService.findAll();
    }

    /**
     * GET  /familias/:id : get the "id" familia.
     *
     * @param id the id of the familia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familia, or with status 404 (Not Found)
     */
    @GetMapping("/familias/{id}")
    @Timed
    public ResponseEntity<Familia> getFamilia(@PathVariable Long id) {
        log.debug("REST request to get Familia : {}", id);
        Optional<Familia> familia = familiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familia);
    }

    /**
     * DELETE  /familias/:id : delete the "id" familia.
     *
     * @param id the id of the familia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/familias/{id}")
    @Timed
    public ResponseEntity<Void> deleteFamilia(@PathVariable Long id) {
        log.debug("REST request to delete Familia : {}", id);
        familiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
