package ar.edu.um.turnos.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.edu.um.turnos.domain.ClinicHistory;
import ar.edu.um.turnos.service.ClinicHistoryService;
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
 * REST controller for managing ClinicHistory.
 */
@RestController
@RequestMapping("/api")
public class ClinicHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ClinicHistoryResource.class);

    private static final String ENTITY_NAME = "clinicHistory";

    private final ClinicHistoryService clinicHistoryService;

    public ClinicHistoryResource(ClinicHistoryService clinicHistoryService) {
        this.clinicHistoryService = clinicHistoryService;
    }

    /**
     * POST  /clinic-histories : Create a new clinicHistory.
     *
     * @param clinicHistory the clinicHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clinicHistory, or with status 400 (Bad Request) if the clinicHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clinic-histories")
    @Timed
    public ResponseEntity<ClinicHistory> createClinicHistory(@Valid @RequestBody ClinicHistory clinicHistory) throws URISyntaxException {
        log.debug("REST request to save ClinicHistory : {}", clinicHistory);
        if (clinicHistory.getId() != null) {
            throw new BadRequestAlertException("A new clinicHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClinicHistory result = clinicHistoryService.save(clinicHistory);
        return ResponseEntity.created(new URI("/api/clinic-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinic-histories : Updates an existing clinicHistory.
     *
     * @param clinicHistory the clinicHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clinicHistory,
     * or with status 400 (Bad Request) if the clinicHistory is not valid,
     * or with status 500 (Internal Server Error) if the clinicHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clinic-histories")
    @Timed
    public ResponseEntity<ClinicHistory> updateClinicHistory(@Valid @RequestBody ClinicHistory clinicHistory) throws URISyntaxException {
        log.debug("REST request to update ClinicHistory : {}", clinicHistory);
        if (clinicHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClinicHistory result = clinicHistoryService.save(clinicHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clinicHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinic-histories : get all the clinicHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clinicHistories in body
     */
    @GetMapping("/clinic-histories")
    @Timed
    public ResponseEntity<List<ClinicHistory>> getAllClinicHistories(Pageable pageable) {
        log.debug("REST request to get a page of ClinicHistories");
        Page<ClinicHistory> page = clinicHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clinic-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clinic-histories/:id : get the "id" clinicHistory.
     *
     * @param id the id of the clinicHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clinicHistory, or with status 404 (Not Found)
     */
    @GetMapping("/clinic-histories/{id}")
    @Timed
    public ResponseEntity<ClinicHistory> getClinicHistory(@PathVariable Long id) {
        log.debug("REST request to get ClinicHistory : {}", id);
        Optional<ClinicHistory> clinicHistory = clinicHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clinicHistory);
    }

    /**
     * DELETE  /clinic-histories/:id : delete the "id" clinicHistory.
     *
     * @param id the id of the clinicHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clinic-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteClinicHistory(@PathVariable Long id) {
        log.debug("REST request to delete ClinicHistory : {}", id);
        clinicHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
