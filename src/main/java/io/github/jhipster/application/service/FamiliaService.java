package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Familia;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Familia.
 */
public interface FamiliaService {

    /**
     * Save a familia.
     *
     * @param familia the entity to save
     * @return the persisted entity
     */
    Familia save(Familia familia);

    /**
     * Get all the familias.
     *
     * @return the list of entities
     */
    List<Familia> findAll();


    /**
     * Get the "id" familia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Familia> findOne(Long id);

    /**
     * Delete the "id" familia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
