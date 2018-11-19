package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Impuesto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Impuesto.
 */
public interface ImpuestoService {

    /**
     * Save a impuesto.
     *
     * @param impuesto the entity to save
     * @return the persisted entity
     */
    Impuesto save(Impuesto impuesto);

    /**
     * Get all the impuestos.
     *
     * @return the list of entities
     */
    List<Impuesto> findAll();


    /**
     * Get the "id" impuesto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Impuesto> findOne(Long id);

    /**
     * Delete the "id" impuesto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
