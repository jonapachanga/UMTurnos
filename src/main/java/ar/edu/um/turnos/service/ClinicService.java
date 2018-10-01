package ar.edu.um.turnos.service;

import ar.edu.um.turnos.domain.Clinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Clinic.
 */
public interface ClinicService {

    /**
     * Save a clinic.
     *
     * @param clinic the entity to save
     * @return the persisted entity
     */
    Clinic save(Clinic clinic);

    /**
     * Get all the clinics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Clinic> findAll(Pageable pageable);


    /**
     * Get the "id" clinic.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Clinic> findOne(Long id);

    /**
     * Delete the "id" clinic.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
