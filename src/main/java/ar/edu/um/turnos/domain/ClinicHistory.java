package ar.edu.um.turnos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ClinicHistory.
 */
@Entity
@Table(name = "clinic_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClinicHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_and_hour", nullable = false)
    private ZonedDateTime dateAndHour;

    @NotNull
    @Column(name = "issue", nullable = false)
    private String issue;

    @NotNull
    @Column(name = "history", nullable = false)
    private String history;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clinicHistories")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateAndHour() {
        return dateAndHour;
    }

    public ClinicHistory dateAndHour(ZonedDateTime dateAndHour) {
        this.dateAndHour = dateAndHour;
        return this;
    }

    public void setDateAndHour(ZonedDateTime dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    public String getIssue() {
        return issue;
    }

    public ClinicHistory issue(String issue) {
        this.issue = issue;
        return this;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getHistory() {
        return history;
    }

    public ClinicHistory history(String history) {
        this.history = history;
        return this;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Patient getPatient() {
        return patient;
    }

    public ClinicHistory patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
        ClinicHistory clinicHistory = (ClinicHistory) o;
        if (clinicHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clinicHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClinicHistory{" +
            "id=" + getId() +
            ", dateAndHour='" + getDateAndHour() + "'" +
            ", issue='" + getIssue() + "'" +
            ", history='" + getHistory() + "'" +
            "}";
    }
}
