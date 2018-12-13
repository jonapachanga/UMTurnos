package ar.edu.um.turnos.service.impl;

import ar.edu.um.turnos.service.TurnService;
import ar.edu.um.turnos.domain.Turn;
import ar.edu.um.turnos.repository.TurnRepository;
import ar.edu.um.turnos.service.dto.TurnDTO;
import ar.edu.um.turnos.service.mapper.TurnMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
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
import ar.edu.um.turnos.domain.QTurn;

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


   /** Get all the turns.
     *
     * @param pageable the pagination information
     * @return the list of entities*/

    @Override
    @Transactional(readOnly = true)
    public Page<Turn> findAll(Pageable pageable) {
        log.debug("Request to get all Turns");
        return turnRepository.findAll(pageable);
    }

   /*@Override
    @Transactional(readOnly = true)
    public List<TurnDTO> findByDateAndHour(LocalDate dateAndHour) {
        ZonedDateTime dateAndHourStart = dateAndHour.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime dateAndHourEnd = dateAndHourStart.withHour(23).withMinute(59).withSecond(59);
        log.debug("all Turns filter by date and hour {} {}", dateAndHourStart, dateAndHourEnd);
        return turnMapper.turnsToTurnsDTO(turnRepository.findByDateAndHour(dateAndHourStart, dateAndHourEnd));
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<TurnDTO> findByDateAndHour(LocalDate dateAndHour) {
        BooleanBuilder builder = new BooleanBuilder();
        QTurn qTurn = QTurn.turn;
        ZonedDateTime dateAndHourStart = dateAndHour.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime dateAndHourEnd = dateAndHourStart.withHour(23).withMinute(59).withSecond(59);
        log.debug("all Turns filter by date and hour {} {}", dateAndHourStart, dateAndHourEnd);

        OrderSpecifier<ZonedDateTime> orderSpecifier = qTurn.dateAndHour.desc();
        if (dateAndHour != null) {
            builder.and(qTurn.dateAndHour.between(dateAndHourStart, dateAndHourEnd));
        }


        /*
        if (clinic != null){
            builder.and(qTurn.clinic.eq(clinic));
        }

        if (pacient != null){
            builder.and(qTurn.pacient.fullName.contains(user.getFullName()));
        }
         */

        return turnMapper.turnsToTurnsDTO(turnRepository.findAllDateAndHour(builder, orderSpecifier));
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<TurnDTO> findByDateAndHourQ(LocalDate dateAndHour) {
//        BooleanBuilder builder = new BooleanBuilder();
//        QTurn qTurn = QTurn.turn;
//        OrderSpecifier<ZonedDateTime> orderSpecifier = qTurn.dateAndHour.desc();
//
//       /*if (true) {
//             orderSpecifier = qTurn.dateAndHour.desc();
//        }else{
//            orderSpecifier = qTurn.dateAndHour.asc();
//        }
//        // si el parametro doctor es distinto de null
//        if (true) {
//            builder.and(qTurn.user.firstName.contains("mercaddo"));
//        }
//
//        //Si parametro paciente es distinto de null
//        if (true) {
//            builder.or(qTurn.patient.fullName.contains("martinez"));
//        }*/
//
//        ZonedDateTime dateAndHourStart = dateAndHour.atStartOfDay(ZoneId.systemDefault());
//        ZonedDateTime dateAndHourEnd = dateAndHourStart.withHour(23).withMinute(59).withSecond(59);
//        BooleanExpression bexp = qTurn.patient.fullName.contains("brend");
//
//        log.debug("all Turns filter by date and hour {} {}", dateAndHourStart, dateAndHourEnd);
//        return turnMapper.turnsToTurnsDTO(turnRepository.findAllByDateAndHour(bexp));
//    }



    /*
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
