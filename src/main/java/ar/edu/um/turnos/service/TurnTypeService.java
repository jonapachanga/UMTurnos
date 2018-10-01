package ar.edu.um.turnos.service;

import ar.edu.um.turnos.domain.TurnType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TurnType.
 */
public interface TurnTypeService {

    /**
     * Save a turnType.
     *
     * @param turnType the entity to save
     * @return the persisted entity
     */
    TurnType save(TurnType turnType);

    /**
     * Get all the turnTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TurnType> findAll(Pageable pageable);


    /**
     * Get the "id" turnType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TurnType> findOne(Long id);

    /**
     * Delete the "id" turnType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
