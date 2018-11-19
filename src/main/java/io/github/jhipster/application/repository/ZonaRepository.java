package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Zona;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Zona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {

}
