package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Almacen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Almacen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {

}
