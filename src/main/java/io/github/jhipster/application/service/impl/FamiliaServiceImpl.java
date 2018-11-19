package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.FamiliaService;
import io.github.jhipster.application.domain.Familia;
import io.github.jhipster.application.repository.FamiliaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Familia.
 */
@Service
@Transactional
public class FamiliaServiceImpl implements FamiliaService {

    private final Logger log = LoggerFactory.getLogger(FamiliaServiceImpl.class);

    private final FamiliaRepository familiaRepository;

    public FamiliaServiceImpl(FamiliaRepository familiaRepository) {
        this.familiaRepository = familiaRepository;
    }

    /**
     * Save a familia.
     *
     * @param familia the entity to save
     * @return the persisted entity
     */
    @Override
    public Familia save(Familia familia) {
        log.debug("Request to save Familia : {}", familia);
        return familiaRepository.save(familia);
    }

    /**
     * Get all the familias.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Familia> findAll() {
        log.debug("Request to get all Familias");
        return familiaRepository.findAll();
    }


    /**
     * Get one familia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Familia> findOne(Long id) {
        log.debug("Request to get Familia : {}", id);
        return familiaRepository.findById(id);
    }

    /**
     * Delete the familia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Familia : {}", id);
        familiaRepository.deleteById(id);
    }
}
