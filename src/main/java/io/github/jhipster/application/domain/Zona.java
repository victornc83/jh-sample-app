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
 * A Zona.
 */
@Entity
@Table(name = "zona")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Zona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "zona_name", nullable = false)
    private String zonaName;

    @OneToMany(mappedBy = "zona")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Empleado> empleados = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZonaName() {
        return zonaName;
    }

    public Zona zonaName(String zonaName) {
        this.zonaName = zonaName;
        return this;
    }

    public void setZonaName(String zonaName) {
        this.zonaName = zonaName;
    }

    public Set<Empleado> getEmpleados() {
        return empleados;
    }

    public Zona empleados(Set<Empleado> empleados) {
        this.empleados = empleados;
        return this;
    }

    public Zona addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
        empleado.setZona(this);
        return this;
    }

    public Zona removeEmpleado(Empleado empleado) {
        this.empleados.remove(empleado);
        empleado.setZona(null);
        return this;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
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
        Zona zona = (Zona) o;
        if (zona.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zona.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Zona{" +
            "id=" + getId() +
            ", zonaName='" + getZonaName() + "'" +
            "}";
    }
}
