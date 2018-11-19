package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ArticuloService;
import io.github.jhipster.application.domain.Articulo;
import io.github.jhipster.application.repository.ArticuloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Articulo.
 */
@Service
@Transactional
public class ArticuloServiceImpl implements ArticuloService {

    private final Logger log = LoggerFactory.getLogger(ArticuloServiceImpl.class);

    private final ArticuloRepository articuloRepository;

    public ArticuloServiceImpl(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    /**
     * Save a articulo.
     *
     * @param articulo the entity to save
     * @return the persisted entity
     */
    @Override
    public Articulo save(Articulo articulo) {
        log.debug("Request to save Articulo : {}", articulo);
        return articuloRepository.save(articulo);
    }

    /**
     * Get all the articulos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Articulo> findAll() {
        log.debug("Request to get all Articulos");
        return articuloRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the Articulo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Articulo> findAllWithEagerRelationships(Pageable pageable) {
        return articuloRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one articulo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Articulo> findOne(Long id) {
        log.debug("Request to get Articulo : {}", id);
        return articuloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the articulo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Articulo : {}", id);
        articuloRepository.deleteById(id);
    }
}
