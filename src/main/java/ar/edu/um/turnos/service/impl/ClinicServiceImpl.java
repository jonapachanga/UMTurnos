package ar.edu.um.turnos.service.impl;

import ar.edu.um.turnos.service.ClinicService;
import ar.edu.um.turnos.domain.Clinic;
import ar.edu.um.turnos.repository.ClinicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Clinic.
 */
@Service
@Transactional
public class ClinicServiceImpl implements ClinicService {

    private final Logger log = LoggerFactory.getLogger(ClinicServiceImpl.class);

    private final ClinicRepository clinicRepository;

    public ClinicServiceImpl(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    /**
     * Save a clinic.
     *
     * @param clinic the entity to save
     * @return the persisted entity
     */
    @Override
    public Clinic save(Clinic clinic) {
        log.debug("Request to save Clinic : {}", clinic);        return clinicRepository.save(clinic);
    }

    /**
     * Get all the clinics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Clinic> findAll(Pageable pageable) {
        log.debug("Request to get all Clinics");
        return clinicRepository.findAll(pageable);
    }


    /**
     * Get one clinic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Clinic> findOne(Long id) {
        log.debug("Request to get Clinic : {}", id);
        return clinicRepository.findById(id);
    }

    /**
     * Delete the clinic by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clinic : {}", id);
        clinicRepository.deleteById(id);
    }
}
