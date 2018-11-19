package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Impuesto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Impuesto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {

}
