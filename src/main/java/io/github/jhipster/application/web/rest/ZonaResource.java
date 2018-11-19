package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Zona;
import io.github.jhipster.application.service.ZonaService;
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
 * REST controller for managing Zona.
 */
@RestController
@RequestMapping("/api")
public class ZonaResource {

    private final Logger log = LoggerFactory.getLogger(ZonaResource.class);

    private static final String ENTITY_NAME = "jhSampleAppZona";

    private final ZonaService zonaService;

    public ZonaResource(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    /**
     * POST  /zonas : Create a new zona.
     *
     * @param zona the zona to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zona, or with status 400 (Bad Request) if the zona has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zonas")
    @Timed
    public ResponseEntity<Zona> createZona(@Valid @RequestBody Zona zona) throws URISyntaxException {
        log.debug("REST request to save Zona : {}", zona);
        if (zona.getId() != null) {
            throw new BadRequestAlertException("A new zona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Zona result = zonaService.save(zona);
        return ResponseEntity.created(new URI("/api/zonas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zonas : Updates an existing zona.
     *
     * @param zona the zona to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zona,
     * or with status 400 (Bad Request) if the zona is not valid,
     * or with status 500 (Internal Server Error) if the zona couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zonas")
    @Timed
    public ResponseEntity<Zona> updateZona(@Valid @RequestBody Zona zona) throws URISyntaxException {
        log.debug("REST request to update Zona : {}", zona);
        if (zona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Zona result = zonaService.save(zona);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zona.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zonas : get all the zonas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zonas in body
     */
    @GetMapping("/zonas")
    @Timed
    public List<Zona> getAllZonas() {
        log.debug("REST request to get all Zonas");
        return zonaService.findAll();
    }

    /**
     * GET  /zonas/:id : get the "id" zona.
     *
     * @param id the id of the zona to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zona, or with status 404 (Not Found)
     */
    @GetMapping("/zonas/{id}")
    @Timed
    public ResponseEntity<Zona> getZona(@PathVariable Long id) {
        log.debug("REST request to get Zona : {}", id);
        Optional<Zona> zona = zonaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zona);
    }

    /**
     * DELETE  /zonas/:id : delete the "id" zona.
     *
     * @param id the id of the zona to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zonas/{id}")
    @Timed
    public ResponseEntity<Void> deleteZona(@PathVariable Long id) {
        log.debug("REST request to delete Zona : {}", id);
        zonaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
