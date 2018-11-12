package org.avenue1.avo.service;

import org.avenue1.avo.domain.Vessel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Vessel.
 */
public interface VesselService {

    /**
     * Save a vessel.
     *
     * @param vessel the entity to save
     * @return the persisted entity
     */
    Vessel save(Vessel vessel);

    /**
     * Get all the vessels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Vessel> findAll(Pageable pageable);

    /**
     * Get all the Vessel with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Vessel> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" vessel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Vessel> findOne(String id);

    /**
     * Delete the "id" vessel.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
