package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Almacen;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Almacen.
 */
public interface AlmacenService {

    /**
     * Save a almacen.
     *
     * @param almacen the entity to save
     * @return the persisted entity
     */
    Almacen save(Almacen almacen);

    /**
     * Get all the almacens.
     *
     * @return the list of entities
     */
    List<Almacen> findAll();


    /**
     * Get the "id" almacen.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Almacen> findOne(Long id);

    /**
     * Delete the "id" almacen.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
