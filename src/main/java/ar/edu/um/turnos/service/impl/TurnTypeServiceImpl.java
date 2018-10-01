package ar.edu.um.turnos.service.impl;

import ar.edu.um.turnos.service.TurnTypeService;
import ar.edu.um.turnos.domain.TurnType;
import ar.edu.um.turnos.repository.TurnTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TurnType.
 */
@Service
@Transactional
public class TurnTypeServiceImpl implements TurnTypeService {

    private final Logger log = LoggerFactory.getLogger(TurnTypeServiceImpl.class);

    private final TurnTypeRepository turnTypeRepository;

    public TurnTypeServiceImpl(TurnTypeRepository turnTypeRepository) {
        this.turnTypeRepository = turnTypeRepository;
    }

    /**
     * Save a turnType.
     *
     * @param turnType the entity to save
     * @return the persisted entity
     */
    @Override
    public TurnType save(TurnType turnType) {
        log.debug("Request to save TurnType : {}", turnType);        return turnTypeRepository.save(turnType);
    }

    /**
     * Get all the turnTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TurnType> findAll(Pageable pageable) {
        log.debug("Request to get all TurnTypes");
        return turnTypeRepository.findAll(pageable);
    }


    /**
     * Get one turnType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TurnType> findOne(Long id) {
        log.debug("Request to get TurnType : {}", id);
        return turnTypeRepository.findById(id);
    }

    /**
     * Delete the turnType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TurnType : {}", id);
        turnTypeRepository.deleteById(id);
    }
}
