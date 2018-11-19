package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Impuesto.
 */
@Entity
@Table(name = "impuesto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Impuesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "porcentaje")
    private Float porcentaje;

    @OneToMany(mappedBy = "impuesto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Articulo> nombres = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Impuesto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public Impuesto porcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
        return this;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Set<Articulo> getNombres() {
        return nombres;
    }

    public Impuesto nombres(Set<Articulo> articulos) {
        this.nombres = articulos;
        return this;
    }

    public Impuesto addNombre(Articulo articulo) {
        this.nombres.add(articulo);
        articulo.setImpuesto(this);
        return this;
    }

    public Impuesto removeNombre(Articulo articulo) {
        this.nombres.remove(articulo);
        articulo.setImpuesto(null);
        return this;
    }

    public void setNombres(Set<Articulo> articulos) {
        this.nombres = articulos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Impuesto impuesto = (Impuesto) o;
        if (impuesto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), impuesto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Impuesto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", porcentaje=" + getPorcentaje() +
            "}";
    }
}
