package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Articulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Articulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    @Query(value = "select distinct articulo from Articulo articulo left join fetch articulo.ids",
        countQuery = "select count(distinct articulo) from Articulo articulo")
    Page<Articulo> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct articulo from Articulo articulo left join fetch articulo.ids")
    List<Articulo> findAllWithEagerRelationships();

    @Query("select articulo from Articulo articulo left join fetch articulo.ids where articulo.id =:id")
    Optional<Articulo> findOneWithEagerRelationships(@Param("id") Long id);

}
