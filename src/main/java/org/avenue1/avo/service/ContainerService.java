package org.avenue1.avo.service;

import org.avenue1.avo.domain.Container;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Container.
 */
public interface ContainerService {

    /**
     * Save a container.
     *
     * @param container the entity to save
     * @return the persisted entity
     */
    Container save(Container container);

    /**
     * Get all the containers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Container> findAll(Pageable pageable);


    /**
     * Get the "id" container.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Container> findOne(String id);

    /**
     * Delete the "id" container.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
