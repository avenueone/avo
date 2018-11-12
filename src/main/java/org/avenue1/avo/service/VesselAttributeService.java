package org.avenue1.avo.service;

import org.avenue1.avo.domain.VesselAttribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing VesselAttribute.
 */
public interface VesselAttributeService {

    /**
     * Save a vesselAttribute.
     *
     * @param vesselAttribute the entity to save
     * @return the persisted entity
     */
    VesselAttribute save(VesselAttribute vesselAttribute);

    /**
     * Get all the vesselAttributes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VesselAttribute> findAll(Pageable pageable);


    /**
     * Get the "id" vesselAttribute.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VesselAttribute> findOne(String id);

    /**
     * Delete the "id" vesselAttribute.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
