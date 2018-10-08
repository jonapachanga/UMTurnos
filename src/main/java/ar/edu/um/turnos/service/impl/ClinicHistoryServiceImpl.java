package ar.edu.um.turnos.service.impl;

import ar.edu.um.turnos.service.ClinicHistoryService;
import ar.edu.um.turnos.domain.ClinicHistory;
import ar.edu.um.turnos.repository.ClinicHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ClinicHistory.
 */
@Service
@Transactional
public class ClinicHistoryServiceImpl implements ClinicHistoryService {

    private final Logger log = LoggerFactory.getLogger(ClinicHistoryServiceImpl.class);

    private final ClinicHistoryRepository clinicHistoryRepository;

    public ClinicHistoryServiceImpl(ClinicHistoryRepository clinicHistoryRepository) {
        this.clinicHistoryRepository = clinicHistoryRepository;
    }

    /**
     * Save a clinicHistory.
     *
     * @param clinicHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public ClinicHistory save(ClinicHistory clinicHistory) {
        log.debug("Request to save ClinicHistory : {}", clinicHistory);        return clinicHistoryRepository.save(clinicHistory);
    }

    /**
     * Get all the clinicHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClinicHistory> findAll(Pageable pageable) {
        log.debug("Request to get all ClinicHistories");
        return clinicHistoryRepository.findAll(pageable);
    }


    /**
     * Get one clinicHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClinicHistory> findOne(Long id) {
        log.debug("Request to get ClinicHistory : {}", id);
        return clinicHistoryRepository.findById(id);
    }

    /**
     * Delete the clinicHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClinicHistory : {}", id);
        clinicHistoryRepository.deleteById(id);
    }
}
