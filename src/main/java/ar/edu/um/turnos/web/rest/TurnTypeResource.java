package ar.edu.um.turnos.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.edu.um.turnos.domain.TurnType;
import ar.edu.um.turnos.service.TurnTypeService;
import ar.edu.um.turnos.web.rest.errors.BadRequestAlertException;
import ar.edu.um.turnos.web.rest.util.HeaderUtil;
import ar.edu.um.turnos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TurnType.
 */
@RestController
@RequestMapping("/api")
public class TurnTypeResource {

    private final Logger log = LoggerFactory.getLogger(TurnTypeResource.class);

    private static final String ENTITY_NAME = "turnType";

    private final TurnTypeService turnTypeService;

    public TurnTypeResource(TurnTypeService turnTypeService) {
        this.turnTypeService = turnTypeService;
    }

    /**
     * POST  /turn-types : Create a new turnType.
     *
     * @param turnType the turnType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new turnType, or with status 400 (Bad Request) if the turnType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/turn-types")
    @Timed
    public ResponseEntity<TurnType> createTurnType(@Valid @RequestBody TurnType turnType) throws URISyntaxException {
        log.debug("REST request to save TurnType : {}", turnType);
        if (turnType.getId() != null) {
            throw new BadRequestAlertException("A new turnType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TurnType result = turnTypeService.save(turnType);
        return ResponseEntity.created(new URI("/api/turn-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /turn-types : Updates an existing turnType.
     *
     * @param turnType the turnType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated turnType,
     * or with status 400 (Bad Request) if the turnType is not valid,
     * or with status 500 (Internal Server Error) if the turnType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/turn-types")
    @Timed
    public ResponseEntity<TurnType> updateTurnType(@Valid @RequestBody TurnType turnType) throws URISyntaxException {
        log.debug("REST request to update TurnType : {}", turnType);
        if (turnType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TurnType result = turnTypeService.save(turnType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, turnType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /turn-types : get all the turnTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of turnTypes in body
     */
    @GetMapping("/turn-types")
    @Timed
    public ResponseEntity<List<TurnType>> getAllTurnTypes(Pageable pageable) {
        log.debug("REST request to get a page of TurnTypes");
        Page<TurnType> page = turnTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/turn-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /turn-types/:id : get the "id" turnType.
     *
     * @param id the id of the turnType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the turnType, or with status 404 (Not Found)
     */
    @GetMapping("/turn-types/{id}")
    @Timed
    public ResponseEntity<TurnType> getTurnType(@PathVariable Long id) {
        log.debug("REST request to get TurnType : {}", id);
        Optional<TurnType> turnType = turnTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(turnType);
    }

    /**
     * DELETE  /turn-types/:id : delete the "id" turnType.
     *
     * @param id the id of the turnType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/turn-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTurnType(@PathVariable Long id) {
        log.debug("REST request to delete TurnType : {}", id);
        turnTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
