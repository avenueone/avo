package org.avenue1.avo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.avenue1.avo.domain.CampaignAttribute;
import org.avenue1.avo.service.CampaignAttributeService;
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
 * REST controller for managing CampaignAttribute.
 */
@RestController
@RequestMapping("/api")
public class CampaignAttributeResource {

    private final Logger log = LoggerFactory.getLogger(CampaignAttributeResource.class);

    private static final String ENTITY_NAME = "campaignAttribute";

    private final CampaignAttributeService campaignAttributeService;

    public CampaignAttributeResource(CampaignAttributeService campaignAttributeService) {
        this.campaignAttributeService = campaignAttributeService;
    }

    /**
     * POST  /campaign-attributes : Create a new campaignAttribute.
     *
     * @param campaignAttribute the campaignAttribute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaignAttribute, or with status 400 (Bad Request) if the campaignAttribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campaign-attributes")
    @Timed
    public ResponseEntity<CampaignAttribute> createCampaignAttribute(@RequestBody CampaignAttribute campaignAttribute) throws URISyntaxException {
        log.debug("REST request to save CampaignAttribute : {}", campaignAttribute);
        if (campaignAttribute.getId() != null) {
            throw new BadRequestAlertException("A new campaignAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignAttribute result = campaignAttributeService.save(campaignAttribute);
        return ResponseEntity.created(new URI("/api/campaign-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /campaign-attributes : Updates an existing campaignAttribute.
     *
     * @param campaignAttribute the campaignAttribute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campaignAttribute,
     * or with status 400 (Bad Request) if the campaignAttribute is not valid,
     * or with status 500 (Internal Server Error) if the campaignAttribute couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campaign-attributes")
    @Timed
    public ResponseEntity<CampaignAttribute> updateCampaignAttribute(@RequestBody CampaignAttribute campaignAttribute) throws URISyntaxException {
        log.debug("REST request to update CampaignAttribute : {}", campaignAttribute);
        if (campaignAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampaignAttribute result = campaignAttributeService.save(campaignAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campaignAttribute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /campaign-attributes : get all the campaignAttributes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of campaignAttributes in body
     */
    @GetMapping("/campaign-attributes")
    @Timed
    public ResponseEntity<List<CampaignAttribute>> getAllCampaignAttributes(Pageable pageable) {
        log.debug("REST request to get a page of CampaignAttributes");
        Page<CampaignAttribute> page = campaignAttributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campaign-attributes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /campaign-attributes/:id : get the "id" campaignAttribute.
     *
     * @param id the id of the campaignAttribute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campaignAttribute, or with status 404 (Not Found)
     */
    @GetMapping("/campaign-attributes/{id}")
    @Timed
    public ResponseEntity<CampaignAttribute> getCampaignAttribute(@PathVariable String id) {
        log.debug("REST request to get CampaignAttribute : {}", id);
        Optional<CampaignAttribute> campaignAttribute = campaignAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignAttribute);
    }

    /**
     * DELETE  /campaign-attributes/:id : delete the "id" campaignAttribute.
     *
     * @param id the id of the campaignAttribute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campaign-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCampaignAttribute(@PathVariable String id) {
        log.debug("REST request to delete CampaignAttribute : {}", id);
        campaignAttributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
