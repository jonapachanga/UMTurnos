package ar.edu.um.turnos.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.edu.um.turnos.domain.Clinic;
import ar.edu.um.turnos.service.ClinicService;
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
import ar.edu.um.turnos.security.AuthoritiesConstants;
//import org.springframewirk.security.access.annotation.Secured;
import org.springframework.security.access.annotation.Secured;


import java.lang.String;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Clinic.
 */
@RestController
@RequestMapping("/api")
public class ClinicResource {

    private final Logger log = LoggerFactory.getLogger(ClinicResource.class);

    private static final String ENTITY_NAME = "clinic";

    private final ClinicService clinicService;

    public ClinicResource(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    /**
     * POST  /clinics : Create a new clinic.
     *
     * @param clinic the clinic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clinic, or with status 400 (Bad Request) if the clinic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clinics")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody Clinic clinic) throws URISyntaxException {
        log.debug("REST request to save Clinic : {}", clinic);
        if (clinic.getId() != null) {
            throw new BadRequestAlertException("A new clinic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Clinic result = clinicService.save(clinic);
        return ResponseEntity.created(new URI("/api/clinics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinics : Updates an existing clinic.
     *
     * @param clinic the clinic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clinic,
     * or with status 400 (Bad Request) if the clinic is not valid,
     * or with status 500 (Internal Server Error) if the clinic couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clinics")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<Clinic> updateClinic(@Valid @RequestBody Clinic clinic) throws URISyntaxException {
        log.debug("REST request to update Clinic : {}", clinic);
        if (clinic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Clinic result = clinicService.save(clinic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clinic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinics : get all the clinics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clinics in body
     */
    @GetMapping("/clinics")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.DOCTOR})
    public ResponseEntity<List<Clinic>> getAllClinics(Pageable pageable) {
        log.debug("REST request to get a page of Clinics");
        Page<Clinic> page = clinicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clinics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clinics/:id : get the "id" clinic.
     *
     * @param id the id of the clinic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clinic, or with status 404 (Not Found)
     */
    @GetMapping("/clinics/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SECRETARY, AuthoritiesConstants.DOCTOR })
    public ResponseEntity<Clinic> getClinic(@PathVariable Long id) {
        log.debug("REST request to get Clinic : {}", id);
        Optional<Clinic> clinic = clinicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clinic);
    }

    /**
     * DELETE  /clinics/:id : delete the "id" clinic.
     *
     * @param id the id of the clinic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clinics/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        log.debug("REST request to delete Clinic : {}", id);
        clinicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
