package org.avenue1.avo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.avenue1.avo.domain.VesselAttribute;
import org.avenue1.avo.service.VesselAttributeService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VesselAttribute.
 */
@RestController
@RequestMapping("/api")
public class VesselAttributeResource {

    private final Logger log = LoggerFactory.getLogger(VesselAttributeResource.class);

    private static final String ENTITY_NAME = "vesselAttribute";

    private final VesselAttributeService vesselAttributeService;

    public VesselAttributeResource(VesselAttributeService vesselAttributeService) {
        this.vesselAttributeService = vesselAttributeService;
    }

    /**
     * POST  /vessel-attributes : Create a new vesselAttribute.
     *
     * @param vesselAttribute the vesselAttribute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vesselAttribute, or with status 400 (Bad Request) if the vesselAttribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vessel-attributes")
    @Timed
    public ResponseEntity<VesselAttribute> createVesselAttribute(@RequestBody VesselAttribute vesselAttribute) throws URISyntaxException {
        log.debug("REST request to save VesselAttribute : {}", vesselAttribute);
        if (vesselAttribute.getId() != null) {
            throw new BadRequestAlertException("A new vesselAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VesselAttribute result = vesselAttributeService.save(vesselAttribute);
        return ResponseEntity.created(new URI("/api/vessel-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vessel-attributes : Updates an existing vesselAttribute.
     *
     * @param vesselAttribute the vesselAttribute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vesselAttribute,
     * or with status 400 (Bad Request) if the vesselAttribute is not valid,
     * or with status 500 (Internal Server Error) if the vesselAttribute couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vessel-attributes")
    @Timed
    public ResponseEntity<VesselAttribute> updateVesselAttribute(@RequestBody VesselAttribute vesselAttribute) throws URISyntaxException {
        log.debug("REST request to update VesselAttribute : {}", vesselAttribute);
        if (vesselAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VesselAttribute result = vesselAttributeService.save(vesselAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vesselAttribute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vessel-attributes : get all the vesselAttributes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vesselAttributes in body
     */
    @GetMapping("/vessel-attributes")
    @Timed
    public ResponseEntity<List<VesselAttribute>> getAllVesselAttributes(Pageable pageable) {
        log.debug("REST request to get a page of VesselAttributes");
        Page<VesselAttribute> page = vesselAttributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vessel-attributes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /vessel-attributes/:id : get the "id" vesselAttribute.
     *
     * @param id the id of the vesselAttribute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vesselAttribute, or with status 404 (Not Found)
     */
    @GetMapping("/vessel-attributes/{id}")
    @Timed
    public ResponseEntity<VesselAttribute> getVesselAttribute(@PathVariable String id) {
        log.debug("REST request to get VesselAttribute : {}", id);
        Optional<VesselAttribute> vesselAttribute = vesselAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vesselAttribute);
    }

    /**
     * DELETE  /vessel-attributes/:id : delete the "id" vesselAttribute.
     *
     * @param id the id of the vesselAttribute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vessel-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVesselAttribute(@PathVariable String id) {
        log.debug("REST request to delete VesselAttribute : {}", id);
        vesselAttributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
