package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Zona;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Zona.
 */
public interface ZonaService {

    /**
     * Save a zona.
     *
     * @param zona the entity to save
     * @return the persisted entity
     */
    Zona save(Zona zona);

    /**
     * Get all the zonas.
     *
     * @return the list of entities
     */
    List<Zona> findAll();


    /**
     * Get the "id" zona.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Zona> findOne(Long id);

    /**
     * Delete the "id" zona.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
