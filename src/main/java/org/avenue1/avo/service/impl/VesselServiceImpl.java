package org.avenue1.avo.service.impl;

import org.avenue1.avo.service.VesselService;
import org.avenue1.avo.domain.Vessel;
import org.avenue1.avo.repository.VesselRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Vessel.
 */
@Service
public class VesselServiceImpl implements VesselService {

    private final Logger log = LoggerFactory.getLogger(VesselServiceImpl.class);

    private final VesselRepository vesselRepository;

    public VesselServiceImpl(VesselRepository vesselRepository) {
        this.vesselRepository = vesselRepository;
    }

    /**
     * Save a vessel.
     *
     * @param vessel the entity to save
     * @return the persisted entity
     */
    @Override
    public Vessel save(Vessel vessel) {
        log.debug("Request to save Vessel : {}", vessel);
        return vesselRepository.save(vessel);
    }

    /**
     * Get all the vessels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Vessel> findAll(Pageable pageable) {
        log.debug("Request to get all Vessels");
        return vesselRepository.findAll(pageable);
    }

    /**
     * Get all the Vessel with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Vessel> findAllWithEagerRelationships(Pageable pageable) {
        return vesselRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one vessel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Vessel> findOne(String id) {
        log.debug("Request to get Vessel : {}", id);
        return vesselRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the vessel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Vessel : {}", id);
        vesselRepository.deleteById(id);
    }
}
