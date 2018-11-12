package org.avenue1.avo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.avenue1.avo.domain.VesselType;
import org.avenue1.avo.service.VesselTypeService;
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
 * REST controller for managing VesselType.
 */
@RestController
@RequestMapping("/api")
public class VesselTypeResource {

    private final Logger log = LoggerFactory.getLogger(VesselTypeResource.class);

    private static final String ENTITY_NAME = "vesselType";

    private final VesselTypeService vesselTypeService;

    public VesselTypeResource(VesselTypeService vesselTypeService) {
        this.vesselTypeService = vesselTypeService;
    }

    /**
     * POST  /vessel-types : Create a new vesselType.
     *
     * @param vesselType the vesselType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vesselType, or with status 400 (Bad Request) if the vesselType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vessel-types")
    @Timed
    public ResponseEntity<VesselType> createVesselType(@Valid @RequestBody VesselType vesselType) throws URISyntaxException {
        log.debug("REST request to save VesselType : {}", vesselType);
        if (vesselType.getId() != null) {
            throw new BadRequestAlertException("A new vesselType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VesselType result = vesselTypeService.save(vesselType);
        return ResponseEntity.created(new URI("/api/vessel-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vessel-types : Updates an existing vesselType.
     *
     * @param vesselType the vesselType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vesselType,
     * or with status 400 (Bad Request) if the vesselType is not valid,
     * or with status 500 (Internal Server Error) if the vesselType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vessel-types")
    @Timed
    public ResponseEntity<VesselType> updateVesselType(@Valid @RequestBody VesselType vesselType) throws URISyntaxException {
        log.debug("REST request to update VesselType : {}", vesselType);
        if (vesselType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VesselType result = vesselTypeService.save(vesselType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vesselType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vessel-types : get all the vesselTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vesselTypes in body
     */
    @GetMapping("/vessel-types")
    @Timed
    public ResponseEntity<List<VesselType>> getAllVesselTypes(Pageable pageable) {
        log.debug("REST request to get a page of VesselTypes");
        Page<VesselType> page = vesselTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vessel-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /vessel-types/:id : get the "id" vesselType.
     *
     * @param id the id of the vesselType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vesselType, or with status 404 (Not Found)
     */
    @GetMapping("/vessel-types/{id}")
    @Timed
    public ResponseEntity<VesselType> getVesselType(@PathVariable String id) {
        log.debug("REST request to get VesselType : {}", id);
        Optional<VesselType> vesselType = vesselTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vesselType);
    }

    /**
     * DELETE  /vessel-types/:id : delete the "id" vesselType.
     *
     * @param id the id of the vesselType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vessel-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteVesselType(@PathVariable String id) {
        log.debug("REST request to delete VesselType : {}", id);
        vesselTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
