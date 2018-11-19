package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Accion;

/**
 * A Operacion.
 */
@Entity
@Table(name = "operacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "accion")
    private Accion accion;

    @Column(name = "cantidad")
    private Long cantidad;

    @OneToOne    @JoinColumn(unique = true)
    private Articulo articulo;

    @OneToOne    @JoinColumn(unique = true)
    private Cuenta cuenta;

    @ManyToOne
    @JsonIgnoreProperties("operacions")
    private Empleado empleado;

    @ManyToOne
    @JsonIgnoreProperties("operaciones")
    private Cuenta cuenta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Operacion fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Accion getAccion() {
        return accion;
    }

    public Operacion accion(Accion accion) {
        this.accion = accion;
        return this;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public Operacion cantidad(Long cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public Operacion articulo(Articulo articulo) {
        this.articulo = articulo;
        return this;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public Operacion cuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
        return this;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Operacion empleado(Empleado empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public Operacion cuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
        return this;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
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
        Operacion operacion = (Operacion) o;
        if (operacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operacion{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", accion='" + getAccion() + "'" +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
