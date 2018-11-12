package org.avenue1.avo.service.impl;

import org.avenue1.avo.service.ContainerService;
import org.avenue1.avo.domain.Container;
import org.avenue1.avo.repository.ContainerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Container.
 */
@Service
public class ContainerServiceImpl implements ContainerService {

    private final Logger log = LoggerFactory.getLogger(ContainerServiceImpl.class);

    private final ContainerRepository containerRepository;

    public ContainerServiceImpl(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * Save a container.
     *
     * @param container the entity to save
     * @return the persisted entity
     */
    @Override
    public Container save(Container container) {
        log.debug("Request to save Container : {}", container);
        return containerRepository.save(container);
    }

    /**
     * Get all the containers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Container> findAll(Pageable pageable) {
        log.debug("Request to get all Containers");
        return containerRepository.findAll(pageable);
    }


    /**
     * Get one container by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Container> findOne(String id) {
        log.debug("Request to get Container : {}", id);
        return containerRepository.findById(id);
    }

    /**
     * Delete the container by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Container : {}", id);
        containerRepository.deleteById(id);
    }
}
