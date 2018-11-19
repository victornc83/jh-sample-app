package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_1")
    private String apellido1;

    @Column(name = "apellido_2")
    private String apellido2;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "salario")
    private Long salario;

    @Column(name = "comision_pct")
    private Long comisionPct;

    @ManyToOne
    @JsonIgnoreProperties("empleados")
    private Zona zona;

    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operacion> operacions = new HashSet<>();
    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cuenta> cuentas = new HashSet<>();
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

    public Empleado nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public Empleado apellido1(String apellido1) {
        this.apellido1 = apellido1;
        return this;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public Empleado apellido2(String apellido2) {
        this.apellido2 = apellido2;
        return this;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public Empleado email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Empleado telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getSalario() {
        return salario;
    }

    public Empleado salario(Long salario) {
        this.salario = salario;
        return this;
    }

    public void setSalario(Long salario) {
        this.salario = salario;
    }

    public Long getComisionPct() {
        return comisionPct;
    }

    public Empleado comisionPct(Long comisionPct) {
        this.comisionPct = comisionPct;
        return this;
    }

    public void setComisionPct(Long comisionPct) {
        this.comisionPct = comisionPct;
    }

    public Zona getZona() {
        return zona;
    }

    public Empleado zona(Zona zona) {
        this.zona = zona;
        return this;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Set<Operacion> getOperacions() {
        return operacions;
    }

    public Empleado operacions(Set<Operacion> operacions) {
        this.operacions = operacions;
        return this;
    }

    public Empleado addOperacion(Operacion operacion) {
        this.operacions.add(operacion);
        operacion.setEmpleado(this);
        return this;
    }

    public Empleado removeOperacion(Operacion operacion) {
        this.operacions.remove(operacion);
        operacion.setEmpleado(null);
        return this;
    }

    public void setOperacions(Set<Operacion> operacions) {
        this.operacions = operacions;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public Empleado cuentas(Set<Cuenta> cuentas) {
        this.cuentas = cuentas;
        return this;
    }

    public Empleado addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        cuenta.setEmpleado(this);
        return this;
    }

    public Empleado removeCuenta(Cuenta cuenta) {
        this.cuentas.remove(cuenta);
        cuenta.setEmpleado(null);
        return this;
    }

    public void setCuentas(Set<Cuenta> cuentas) {
        this.cuentas = cuentas;
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
        Empleado empleado = (Empleado) o;
        if (empleado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empleado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empleado{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido1='" + getApellido1() + "'" +
            ", apellido2='" + getApellido2() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", salario=" + getSalario() +
            ", comisionPct=" + getComisionPct() +
            "}";
    }
}
