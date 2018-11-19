package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cuenta.
 */
@Entity
@Table(name = "cuenta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "descuento")
    private Float descuento;

    @Column(name = "gastos")
    private Float gastos;

    @Column(name = "total")
    private Float total;

    @OneToOne    @JoinColumn(unique = true)
    private Empleado empleado;

    @OneToMany(mappedBy = "cuenta")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operacion> operacions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("cuentas")
    private Empleado empleado;

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

    public Cuenta fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Float getDescuento() {
        return descuento;
    }

    public Cuenta descuento(Float descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    public Float getGastos() {
        return gastos;
    }

    public Cuenta gastos(Float gastos) {
        this.gastos = gastos;
        return this;
    }

    public void setGastos(Float gastos) {
        this.gastos = gastos;
    }

    public Float getTotal() {
        return total;
    }

    public Cuenta total(Float total) {
        this.total = total;
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Cuenta empleado(Empleado empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Set<Operacion> getOperacions() {
        return operacions;
    }

    public Cuenta operacions(Set<Operacion> operacions) {
        this.operacions = operacions;
        return this;
    }

    public Cuenta addOperacion(Operacion operacion) {
        this.operacions.add(operacion);
        operacion.setCuenta(this);
        return this;
    }

    public Cuenta removeOperacion(Operacion operacion) {
        this.operacions.remove(operacion);
        operacion.setCuenta(null);
        return this;
    }

    public void setOperacions(Set<Operacion> operacions) {
        this.operacions = operacions;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Cuenta empleado(Empleado empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
        Cuenta cuenta = (Cuenta) o;
        if (cuenta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cuenta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cuenta{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", descuento=" + getDescuento() +
            ", gastos=" + getGastos() +
            ", total=" + getTotal() +
            "}";
    }
}
