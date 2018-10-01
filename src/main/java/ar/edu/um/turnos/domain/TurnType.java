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
 * A TurnType.
 */
@Entity
@Table(name = "turn_types")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TurnType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "turnType")
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

    public TurnType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Turn> getTurns() {
        return turns;
    }

    public TurnType turns(Set<Turn> turns) {
        this.turns = turns;
        return this;
    }

    public TurnType addTurns(Turn turn) {
        this.turns.add(turn);
        turn.setTurnType(this);
        return this;
    }

    public TurnType removeTurns(Turn turn) {
        this.turns.remove(turn);
        turn.setTurnType(null);
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
        TurnType turnType = (TurnType) o;
        if (turnType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turnType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TurnType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
