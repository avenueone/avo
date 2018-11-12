package org.avenue1.avo.service.impl;

import org.avenue1.avo.service.VesselTypeService;
import org.avenue1.avo.domain.VesselType;
import org.avenue1.avo.repository.VesselTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing VesselType.
 */
@Service
public class VesselTypeServiceImpl implements VesselTypeService {

    private final Logger log = LoggerFactory.getLogger(VesselTypeServiceImpl.class);

    private final VesselTypeRepository vesselTypeRepository;

    public VesselTypeServiceImpl(VesselTypeRepository vesselTypeRepository) {
        this.vesselTypeRepository = vesselTypeRepository;
    }

    /**
     * Save a vesselType.
     *
     * @param vesselType the entity to save
     * @return the persisted entity
     */
    @Override
    public VesselType save(VesselType vesselType) {
        log.debug("Request to save VesselType : {}", vesselType);
        return vesselTypeRepository.save(vesselType);
    }

    /**
     * Get all the vesselTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<VesselType> findAll(Pageable pageable) {
        log.debug("Request to get all VesselTypes");
        return vesselTypeRepository.findAll(pageable);
    }


    /**
     * Get one vesselType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<VesselType> findOne(String id) {
        log.debug("Request to get VesselType : {}", id);
        return vesselTypeRepository.findById(id);
    }

    /**
     * Delete the vesselType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VesselType : {}", id);
        vesselTypeRepository.deleteById(id);
    }
}
