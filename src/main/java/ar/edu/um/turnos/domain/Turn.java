package ar.edu.um.turnos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Turn.
 */
@Entity
@Table(name = "turns")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Turn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_and_hour", nullable = false)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private ZonedDateTime dateAndHour;

    @ManyToOne
    @JsonIgnoreProperties("turns")
    private TurnType turnType;

    @ManyToOne
    @JsonIgnoreProperties("turns")
    private Clinic clinic;

    @ManyToOne
    @JsonIgnoreProperties("turns")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

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

    public Turn dateAndHour(ZonedDateTime dateAndHour) {
        this.dateAndHour = dateAndHour;
        return this;
    }

    public void setDateAndHour(ZonedDateTime dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    public TurnType getTurnType() {
        return turnType;
    }

    public Turn turnType(TurnType turnType) {
        this.turnType = turnType;
        return this;
    }

    public void setTurnType(TurnType turnType) {
        this.turnType = turnType;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public Turn clinic(Clinic clinic) {
        this.clinic = clinic;
        return this;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Patient getPatient() {
        return patient;
    }

    public Turn patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getUser() {
        return user;
    }

    public Turn user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Turn turn = (Turn) o;
        if (turn.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turn.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Turn{" +
            "id=" + getId() +
            ", dateAndHour='" + getDateAndHour() + "'" +
            "}";
    }
}
