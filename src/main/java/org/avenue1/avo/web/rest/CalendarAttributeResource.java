package org.avenue1.avo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.avenue1.avo.domain.CalendarAttribute;
import org.avenue1.avo.service.CalendarAttributeService;
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
 * REST controller for managing CalendarAttribute.
 */
@RestController
@RequestMapping("/api")
public class CalendarAttributeResource {

    private final Logger log = LoggerFactory.getLogger(CalendarAttributeResource.class);

    private static final String ENTITY_NAME = "calendarAttribute";

    private final CalendarAttributeService calendarAttributeService;

    public CalendarAttributeResource(CalendarAttributeService calendarAttributeService) {
        this.calendarAttributeService = calendarAttributeService;
    }

    /**
     * POST  /calendar-attributes : Create a new calendarAttribute.
     *
     * @param calendarAttribute the calendarAttribute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calendarAttribute, or with status 400 (Bad Request) if the calendarAttribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calendar-attributes")
    @Timed
    public ResponseEntity<CalendarAttribute> createCalendarAttribute(@RequestBody CalendarAttribute calendarAttribute) throws URISyntaxException {
        log.debug("REST request to save CalendarAttribute : {}", calendarAttribute);
        if (calendarAttribute.getId() != null) {
            throw new BadRequestAlertException("A new calendarAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalendarAttribute result = calendarAttributeService.save(calendarAttribute);
        return ResponseEntity.created(new URI("/api/calendar-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calendar-attributes : Updates an existing calendarAttribute.
     *
     * @param calendarAttribute the calendarAttribute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calendarAttribute,
     * or with status 400 (Bad Request) if the calendarAttribute is not valid,
     * or with status 500 (Internal Server Error) if the calendarAttribute couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calendar-attributes")
    @Timed
    public ResponseEntity<CalendarAttribute> updateCalendarAttribute(@RequestBody CalendarAttribute calendarAttribute) throws URISyntaxException {
        log.debug("REST request to update CalendarAttribute : {}", calendarAttribute);
        if (calendarAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalendarAttribute result = calendarAttributeService.save(calendarAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calendarAttribute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calendar-attributes : get all the calendarAttributes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of calendarAttributes in body
     */
    @GetMapping("/calendar-attributes")
    @Timed
    public ResponseEntity<List<CalendarAttribute>> getAllCalendarAttributes(Pageable pageable) {
        log.debug("REST request to get a page of CalendarAttributes");
        Page<CalendarAttribute> page = calendarAttributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/calendar-attributes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /calendar-attributes/:id : get the "id" calendarAttribute.
     *
     * @param id the id of the calendarAttribute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calendarAttribute, or with status 404 (Not Found)
     */
    @GetMapping("/calendar-attributes/{id}")
    @Timed
    public ResponseEntity<CalendarAttribute> getCalendarAttribute(@PathVariable String id) {
        log.debug("REST request to get CalendarAttribute : {}", id);
        Optional<CalendarAttribute> calendarAttribute = calendarAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarAttribute);
    }

    /**
     * DELETE  /calendar-attributes/:id : delete the "id" calendarAttribute.
     *
     * @param id the id of the calendarAttribute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calendar-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalendarAttribute(@PathVariable String id) {
        log.debug("REST request to delete CalendarAttribute : {}", id);
        calendarAttributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
