package org.avenue1.avo.service.impl;

import org.avenue1.avo.service.CampaignAttributeService;
import org.avenue1.avo.domain.CampaignAttribute;
import org.avenue1.avo.repository.CampaignAttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing CampaignAttribute.
 */
@Service
public class CampaignAttributeServiceImpl implements CampaignAttributeService {

    private final Logger log = LoggerFactory.getLogger(CampaignAttributeServiceImpl.class);

    private final CampaignAttributeRepository campaignAttributeRepository;

    public CampaignAttributeServiceImpl(CampaignAttributeRepository campaignAttributeRepository) {
        this.campaignAttributeRepository = campaignAttributeRepository;
    }

    /**
     * Save a campaignAttribute.
     *
     * @param campaignAttribute the entity to save
     * @return the persisted entity
     */
    @Override
    public CampaignAttribute save(CampaignAttribute campaignAttribute) {
        log.debug("Request to save CampaignAttribute : {}", campaignAttribute);
        return campaignAttributeRepository.save(campaignAttribute);
    }

    /**
     * Get all the campaignAttributes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<CampaignAttribute> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignAttributes");
        return campaignAttributeRepository.findAll(pageable);
    }


    /**
     * Get one campaignAttribute by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<CampaignAttribute> findOne(String id) {
        log.debug("Request to get CampaignAttribute : {}", id);
        return campaignAttributeRepository.findById(id);
    }

    /**
     * Delete the campaignAttribute by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete CampaignAttribute : {}", id);
        campaignAttributeRepository.deleteById(id);
    }
}
