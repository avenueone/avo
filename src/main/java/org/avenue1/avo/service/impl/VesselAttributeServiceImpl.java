package org.avenue1.avo.service.impl;

import org.avenue1.avo.service.VesselAttributeService;
import org.avenue1.avo.domain.VesselAttribute;
import org.avenue1.avo.repository.VesselAttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing VesselAttribute.
 */
@Service
public class VesselAttributeServiceImpl implements VesselAttributeService {

    private final Logger log = LoggerFactory.getLogger(VesselAttributeServiceImpl.class);

    private final VesselAttributeRepository vesselAttributeRepository;

    public VesselAttributeServiceImpl(VesselAttributeRepository vesselAttributeRepository) {
        this.vesselAttributeRepository = vesselAttributeRepository;
    }

    /**
     * Save a vesselAttribute.
     *
     * @param vesselAttribute the entity to save
     * @return the persisted entity
     */
    @Override
    public VesselAttribute save(VesselAttribute vesselAttribute) {
        log.debug("Request to save VesselAttribute : {}", vesselAttribute);
        return vesselAttributeRepository.save(vesselAttribute);
    }

    /**
     * Get all the vesselAttributes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<VesselAttribute> findAll(Pageable pageable) {
        log.debug("Request to get all VesselAttributes");
        return vesselAttributeRepository.findAll(pageable);
    }


    /**
     * Get one vesselAttribute by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<VesselAttribute> findOne(String id) {
        log.debug("Request to get VesselAttribute : {}", id);
        return vesselAttributeRepository.findById(id);
    }

    /**
     * Delete the vesselAttribute by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VesselAttribute : {}", id);
        vesselAttributeRepository.deleteById(id);
    }
}
