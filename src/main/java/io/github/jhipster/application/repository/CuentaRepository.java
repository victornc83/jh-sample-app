package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Cuenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cuenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

}
