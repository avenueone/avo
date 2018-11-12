package org.avenue1.avo.service;

import org.avenue1.avo.domain.CampaignAttribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CampaignAttribute.
 */
public interface CampaignAttributeService {

    /**
     * Save a campaignAttribute.
     *
     * @param campaignAttribute the entity to save
     * @return the persisted entity
     */
    CampaignAttribute save(CampaignAttribute campaignAttribute);

    /**
     * Get all the campaignAttributes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CampaignAttribute> findAll(Pageable pageable);


    /**
     * Get the "id" campaignAttribute.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CampaignAttribute> findOne(String id);

    /**
     * Delete the "id" campaignAttribute.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
