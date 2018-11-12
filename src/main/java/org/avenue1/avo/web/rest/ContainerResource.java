package org.avenue1.avo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.avenue1.avo.domain.Container;
import org.avenue1.avo.service.ContainerService;
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
 * REST controller for managing Container.
 */
@RestController
@RequestMapping("/api")
public class ContainerResource {

    private final Logger log = LoggerFactory.getLogger(ContainerResource.class);

    private static final String ENTITY_NAME = "container";

    private final ContainerService containerService;

    public ContainerResource(ContainerService containerService) {
        this.containerService = containerService;
    }

    /**
     * POST  /containers : Create a new container.
     *
     * @param container the container to create
     * @return the ResponseEntity with status 201 (Created) and with body the new container, or with status 400 (Bad Request) if the container has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/containers")
    @Timed
    public ResponseEntity<Container> createContainer(@RequestBody Container container) throws URISyntaxException {
        log.debug("REST request to save Container : {}", container);
        if (container.getId() != null) {
            throw new BadRequestAlertException("A new container cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Container result = containerService.save(container);
        return ResponseEntity.created(new URI("/api/containers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /containers : Updates an existing container.
     *
     * @param container the container to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated container,
     * or with status 400 (Bad Request) if the container is not valid,
     * or with status 500 (Internal Server Error) if the container couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/containers")
    @Timed
    public ResponseEntity<Container> updateContainer(@RequestBody Container container) throws URISyntaxException {
        log.debug("REST request to update Container : {}", container);
        if (container.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Container result = containerService.save(container);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, container.getId().toString()))
            .body(result);
    }

    /**
     * GET  /containers : get all the containers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of containers in body
     */
    @GetMapping("/containers")
    @Timed
    public ResponseEntity<List<Container>> getAllContainers(Pageable pageable) {
        log.debug("REST request to get a page of Containers");
        Page<Container> page = containerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/containers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /containers/:id : get the "id" container.
     *
     * @param id the id of the container to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the container, or with status 404 (Not Found)
     */
    @GetMapping("/containers/{id}")
    @Timed
    public ResponseEntity<Container> getContainer(@PathVariable String id) {
        log.debug("REST request to get Container : {}", id);
        Optional<Container> container = containerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(container);
    }

    /**
     * DELETE  /containers/:id : delete the "id" container.
     *
     * @param id the id of the container to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/containers/{id}")
    @Timed
    public ResponseEntity<Void> deleteContainer(@PathVariable String id) {
        log.debug("REST request to delete Container : {}", id);
        containerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
