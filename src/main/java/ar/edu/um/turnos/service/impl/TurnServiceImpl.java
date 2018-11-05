package ar.edu.um.turnos.service.impl;

import ar.edu.um.turnos.service.TurnService;
import ar.edu.um.turnos.domain.Turn;
import ar.edu.um.turnos.repository.TurnRepository;
import ar.edu.um.turnos.service.dto.TurnDTO;
import ar.edu.um.turnos.service.mapper.TurnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Turn.
 */
@Service
@Transactional
public class TurnServiceImpl implements TurnService {

    private final Logger log = LoggerFactory.getLogger(TurnServiceImpl.class);

    private final TurnRepository turnRepository;
    private final TurnMapper turnMapper;

    public TurnServiceImpl(TurnRepository turnRepository, TurnMapper turnMapper) {
        this.turnRepository = turnRepository;
        this.turnMapper = turnMapper;
    }

    /**
     * Save a turn.
     *
     * @param turn the entity to save
     * @return the persisted entity
     */
    @Override
    public Turn save(Turn turn) {
        log.debug("Request to save Turn : {}", turn);
        return turnRepository.save(turn);
    }

    /**
     * Get all the turns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Turn> findAll(Pageable pageable) {
        log.debug("Request to get all Turns");
        return turnRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TurnDTO> findByDateAndHour(LocalDate dateAndHour) {
        ZonedDateTime dateAndHourStart = dateAndHour.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime dateAndHourEnd = dateAndHourStart.withHour(23).withMinute(59).withSecond(59);
        log.debug("all Turns filter by date and hour {} {}", dateAndHourStart, dateAndHourEnd);
        return turnMapper.turnsToTurnsDTO(turnRepository.findByDateAndHour(dateAndHourStart, dateAndHourEnd));
    }

    /**
     * Get one turn by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Turn> findOne(Long id) {
        log.debug("Request to get Turn : {}", id);
        return turnRepository.findById(id);
    }

    /**
     * Delete the turn by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Turn : {}", id);
        turnRepository.deleteById(id);
    }
}
