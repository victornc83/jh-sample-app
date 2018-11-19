package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Articulo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Articulo.
 */
public interface ArticuloService {

    /**
     * Save a articulo.
     *
     * @param articulo the entity to save
     * @return the persisted entity
     */
    Articulo save(Articulo articulo);

    /**
     * Get all the articulos.
     *
     * @return the list of entities
     */
    List<Articulo> findAll();

    /**
     * Get all the Articulo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Articulo> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" articulo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Articulo> findOne(Long id);

    /**
     * Delete the "id" articulo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
