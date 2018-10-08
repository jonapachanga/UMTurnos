package ar.edu.um.turnos.service;

import ar.edu.um.turnos.domain.ClinicHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClinicHistory.
 */
public interface ClinicHistoryService {

    /**
     * Save a clinicHistory.
     *
     * @param clinicHistory the entity to save
     * @return the persisted entity
     */
    ClinicHistory save(ClinicHistory clinicHistory);

    /**
     * Get all the clinicHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClinicHistory> findAll(Pageable pageable);


    /**
     * Get the "id" clinicHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClinicHistory> findOne(Long id);

    /**
     * Delete the "id" clinicHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
