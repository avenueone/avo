package org.avenue1.avo.service.impl;

import org.avenue1.avo.service.CampaignService;
import org.avenue1.avo.domain.Campaign;
import org.avenue1.avo.repository.CampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Campaign.
 */
@Service
public class CampaignServiceImpl implements CampaignService {

    private final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    private final CampaignRepository campaignRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    /**
     * Save a campaign.
     *
     * @param campaign the entity to save
     * @return the persisted entity
     */
    @Override
    public Campaign save(Campaign campaign) {
        log.debug("Request to save Campaign : {}", campaign);
        return campaignRepository.save(campaign);
    }

    /**
     * Get all the campaigns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Campaign> findAll(Pageable pageable) {
        log.debug("Request to get all Campaigns");
        return campaignRepository.findAll(pageable);
    }


    /**
     * Get one campaign by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Campaign> findOne(String id) {
        log.debug("Request to get Campaign : {}", id);
        return campaignRepository.findById(id);
    }

    /**
     * Delete the campaign by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Campaign : {}", id);
        campaignRepository.deleteById(id);
    }
}
