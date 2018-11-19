package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Articulo.
 */
@Entity
@Table(name = "articulo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Float precio;

    @OneToOne    @JoinColumn(unique = true)
    private Familia familia;

    @OneToOne    @JoinColumn(unique = true)
    private Impuesto impuesto;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "articulo_id",
               joinColumns = @JoinColumn(name = "articulos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ids_id", referencedColumnName = "id"))
    private Set<Almacen> ids = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("articulos")
    private Familia familia;

    @ManyToOne
    @JsonIgnoreProperties("articulos")
    private Impuesto impuesto;

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

    public Articulo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Articulo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecio() {
        return precio;
    }

    public Articulo precio(Float precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Familia getFamilia() {
        return familia;
    }

    public Articulo familia(Familia familia) {
        this.familia = familia;
        return this;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public Articulo impuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    public Set<Almacen> getIds() {
        return ids;
    }

    public Articulo ids(Set<Almacen> almacens) {
        this.ids = almacens;
        return this;
    }

    public Articulo addId(Almacen almacen) {
        this.ids.add(almacen);
        almacen.getIds().add(this);
        return this;
    }

    public Articulo removeId(Almacen almacen) {
        this.ids.remove(almacen);
        almacen.getIds().remove(this);
        return this;
    }

    public void setIds(Set<Almacen> almacens) {
        this.ids = almacens;
    }

    public Familia getFamilia() {
        return familia;
    }

    public Articulo familia(Familia familia) {
        this.familia = familia;
        return this;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public Articulo impuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
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
        Articulo articulo = (Articulo) o;
        if (articulo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articulo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Articulo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            "}";
    }
}
