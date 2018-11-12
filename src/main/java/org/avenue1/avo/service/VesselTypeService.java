package org.avenue1.avo.service;

import org.avenue1.avo.domain.VesselType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing VesselType.
 */
public interface VesselTypeService {

    /**
     * Save a vesselType.
     *
     * @param vesselType the entity to save
     * @return the persisted entity
     */
    VesselType save(VesselType vesselType);

    /**
     * Get all the vesselTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VesselType> findAll(Pageable pageable);


    /**
     * Get the "id" vesselType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VesselType> findOne(String id);

    /**
     * Delete the "id" vesselType.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
