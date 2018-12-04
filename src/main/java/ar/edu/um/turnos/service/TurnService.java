package ar.edu.um.turnos.service;

import ar.edu.um.turnos.domain.Turn;

import ar.edu.um.turnos.service.dto.TurnDTO;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Turn.
 */
public interface TurnService {

    /**
     * Save a turn.
     *
     * @param turn the entity to save
     * @return the persisted entity
     */
    Turn save(Turn turn);

    /**
     * Get all the turns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Turn> findAll(Pageable pageable);
    List<TurnDTO> findByDateAndHour(LocalDate dateAndHour);
   /* List<TurnDTO> findByDateAndHourQ(LocalDate dateAndHour);*/

    /**
     * Get the "id" turn.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Turn> findOne(Long id);

    /**
     * Delete the "id" turn.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
