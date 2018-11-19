package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ZonaService;
import io.github.jhipster.application.domain.Zona;
import io.github.jhipster.application.repository.ZonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Zona.
 */
@Service
@Transactional
public class ZonaServiceImpl implements ZonaService {

    private final Logger log = LoggerFactory.getLogger(ZonaServiceImpl.class);

    private final ZonaRepository zonaRepository;

    public ZonaServiceImpl(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    /**
     * Save a zona.
     *
     * @param zona the entity to save
     * @return the persisted entity
     */
    @Override
    public Zona save(Zona zona) {
        log.debug("Request to save Zona : {}", zona);
        return zonaRepository.save(zona);
    }

    /**
     * Get all the zonas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Zona> findAll() {
        log.debug("Request to get all Zonas");
        return zonaRepository.findAll();
    }


    /**
     * Get one zona by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Zona> findOne(Long id) {
        log.debug("Request to get Zona : {}", id);
        return zonaRepository.findById(id);
    }

    /**
     * Delete the zona by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Zona : {}", id);
        zonaRepository.deleteById(id);
    }
}
