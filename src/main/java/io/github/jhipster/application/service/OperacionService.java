package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Operacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Operacion.
 */
public interface OperacionService {

    /**
     * Save a operacion.
     *
     * @param operacion the entity to save
     * @return the persisted entity
     */
    Operacion save(Operacion operacion);

    /**
     * Get all the operacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Operacion> findAll(Pageable pageable);


    /**
     * Get the "id" operacion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Operacion> findOne(Long id);

    /**
     * Delete the "id" operacion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
