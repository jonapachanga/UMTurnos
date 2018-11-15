package ar.edu.um.turnos.web.rest;

import ar.edu.um.turnos.service.dto.TurnDTO;
import ar.edu.um.turnos.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import ar.edu.um.turnos.domain.Turn;
import ar.edu.um.turnos.service.TurnService;
import ar.edu.um.turnos.web.rest.errors.BadRequestAlertException;
import ar.edu.um.turnos.web.rest.util.HeaderUtil;
import ar.edu.um.turnos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Turn.
 */
@RestController
@RequestMapping("/api")
public class TurnResource {

    private final Logger log = LoggerFactory.getLogger(TurnResource.class);

    private static final String ENTITY_NAME = "turn";

    private final TurnService turnService;

    public TurnResource(TurnService turnService) {
        this.turnService = turnService;
    }

    /**
     * POST  /turns : Create a new turn.
     *
     * @param turn the turn to create
     * @return the ResponseEntity with status 201 (Created) and with body the new turn, or with status 400 (Bad Request) if the turn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/turns")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.SECRETARY, AuthoritiesConstants.DOCTOR})
    public ResponseEntity<Turn> createTurn(@Valid @RequestBody Turn turn) throws URISyntaxException {
        log.debug("REST request to save Turn : {}", turn);
        if (turn.getId() != null) {
            throw new BadRequestAlertException("A new turn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Turn result = turnService.save(turn);
        return ResponseEntity.created(new URI("/api/turns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /turns : Updates an existing turn.
     *
     * @param turn the turn to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated turn,
     * or with status 400 (Bad Request) if the turn is not valid,
     * or with status 500 (Internal Server Error) if the turn couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/turns")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.SECRETARY, AuthoritiesConstants.DOCTOR})
    public ResponseEntity<Turn> updateTurn(@Valid @RequestBody Turn turn) throws URISyntaxException {
        log.debug("REST request to update Turn : {}", turn);
        if (turn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Turn result = turnService.save(turn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, turn.getId().toString()))
            .body(result);
    }

    /**
     * GET  /turns : get all the turns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of turns in body
     */
    @GetMapping("/turns")
    @Timed
    public ResponseEntity<List<Turn>> getAllTurns(Pageable pageable) {
        log.debug("REST request to get a page of Turns");
        Page<Turn> page = turnService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/turns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/turns/date/{date}")
    @Timed
    public ResponseEntity<List<TurnDTO>> findByDateAndHour(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.debug("REST request to get a list of turns with date and hour {}", date);
        List<TurnDTO> turnList = turnService.findByDateAndHour(date);
        return new ResponseEntity<>(turnList, HttpStatus.OK);
    }

    /**
     * GET  /turns/:id : get the "id" turn.
     *
     * @param id the id of the turn to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the turn, or with status 404 (Not Found)
     */
    @GetMapping("/turns/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.SECRETARY, AuthoritiesConstants.DOCTOR})
    public ResponseEntity<Turn> getTurn(@PathVariable Long id) {
        log.debug("REST request to get Turn : {}", id);
        Optional<Turn> turn = turnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(turn);
    }

    /**
     * DELETE  /turns/:id : delete the "id" turn.
     *
     * @param id the id of the turn to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/turns/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.SECRETARY, AuthoritiesConstants.DOCTOR})
    public ResponseEntity<Void> deleteTurn(@PathVariable Long id) {
        log.debug("REST request to delete Turn : {}", id);
        turnService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
