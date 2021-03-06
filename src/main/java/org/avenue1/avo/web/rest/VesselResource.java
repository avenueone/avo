package org.avenue1.avo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.avenue1.avo.domain.Vessel;
import org.avenue1.avo.service.VesselService;
import org.avenue1.avo.web.rest.errors.BadRequestAlertException;
import org.avenue1.avo.web.rest.util.HeaderUtil;
import org.avenue1.avo.web.rest.util.PaginationUtil;
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
 * REST controller for managing Vessel.
 */
@RestController
@RequestMapping("/api")
public class VesselResource {

    private final Logger log = LoggerFactory.getLogger(VesselResource.class);

    private static final String ENTITY_NAME = "vessel";

    private final VesselService vesselService;

    public VesselResource(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    /**
     * POST  /vessels : Create a new vessel.
     *
     * @param vessel the vessel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vessel, or with status 400 (Bad Request) if the vessel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vessels")
    @Timed
    public ResponseEntity<Vessel> createVessel(@Valid @RequestBody Vessel vessel) throws URISyntaxException {
        log.debug("REST request to save Vessel : {}", vessel);
        if (vessel.getId() != null) {
            throw new BadRequestAlertException("A new vessel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vessel result = vesselService.save(vessel);
        return ResponseEntity.created(new URI("/api/vessels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vessels : Updates an existing vessel.
     *
     * @param vessel the vessel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vessel,
     * or with status 400 (Bad Request) if the vessel is not valid,
     * or with status 500 (Internal Server Error) if the vessel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vessels")
    @Timed
    public ResponseEntity<Vessel> updateVessel(@Valid @RequestBody Vessel vessel) throws URISyntaxException {
        log.debug("REST request to update Vessel : {}", vessel);
        if (vessel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vessel result = vesselService.save(vessel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vessel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vessels : get all the vessels.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of vessels in body
     */
    @GetMapping("/vessels")
    @Timed
    public ResponseEntity<List<Vessel>> getAllVessels(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Vessels");
        Page<Vessel> page;
        if (eagerload) {
            page = vesselService.findAllWithEagerRelationships(pageable);
        } else {
            page = vesselService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/vessels?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /vessels/:id : get the "id" vessel.
     *
     * @param id the id of the vessel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vessel, or with status 404 (Not Found)
     */
    @GetMapping("/vessels/{id}")
    @Timed
    public ResponseEntity<Vessel> getVessel(@PathVariable String id) {
        log.debug("REST request to get Vessel : {}", id);
        Optional<Vessel> vessel = vesselService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vessel);
    }

    /**
     * DELETE  /vessels/:id : delete the "id" vessel.
     *
     * @param id the id of the vessel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vessels/{id}")
    @Timed
    public ResponseEntity<Void> deleteVessel(@PathVariable String id) {
        log.debug("REST request to delete Vessel : {}", id);
        vesselService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
