package ar.edu.um.turnos.domain;

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
 * A Patient.
 */
@Entity
@Table(name = "patients")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "sur_name", nullable = false)
    private String surName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "insurance_mutual")
    private String insuranceMutual;

    @NotNull
    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "number_afiliated")
    private String numberAfiliated;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turn> turns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Patient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public Patient surName(String surName) {
        this.surName = surName;
        return this;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public Patient email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Patient phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public Patient mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public Patient address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInsuranceMutual() {
        return insuranceMutual;
    }

    public Patient insuranceMutual(String insuranceMutual) {
        this.insuranceMutual = insuranceMutual;
        return this;
    }

    public void setInsuranceMutual(String insuranceMutual) {
        this.insuranceMutual = insuranceMutual;
    }

    public String getDni() {
        return dni;
    }

    public Patient dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumberAfiliated() {
        return numberAfiliated;
    }

    public Patient numberAfiliated(String numberAfiliated) {
        this.numberAfiliated = numberAfiliated;
        return this;
    }

    public void setNumberAfiliated(String numberAfiliated) {
        this.numberAfiliated = numberAfiliated;
    }

    public String getNote() {
        return note;
    }

    public Patient note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<Turn> getTurns() {
        return turns;
    }

    public Patient turns(Set<Turn> turns) {
        this.turns = turns;
        return this;
    }

    public Patient addTurns(Turn turn) {
        this.turns.add(turn);
        turn.setPatient(this);
        return this;
    }

    public Patient removeTurns(Turn turn) {
        this.turns.remove(turn);
        turn.setPatient(null);
        return this;
    }

    public void setTurns(Set<Turn> turns) {
        this.turns = turns;
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
        Patient patient = (Patient) o;
        if (patient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surName='" + getSurName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", address='" + getAddress() + "'" +
            ", insuranceMutual='" + getInsuranceMutual() + "'" +
            ", dni='" + getDni() + "'" +
            ", numberAfiliated='" + getNumberAfiliated() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
