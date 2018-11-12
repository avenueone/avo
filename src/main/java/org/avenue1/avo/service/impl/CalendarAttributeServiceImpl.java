package org.avenue1.avo.service.impl;

import org.avenue1.avo.service.CalendarAttributeService;
import org.avenue1.avo.domain.CalendarAttribute;
import org.avenue1.avo.repository.CalendarAttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing CalendarAttribute.
 */
@Service
public class CalendarAttributeServiceImpl implements CalendarAttributeService {

    private final Logger log = LoggerFactory.getLogger(CalendarAttributeServiceImpl.class);

    private final CalendarAttributeRepository calendarAttributeRepository;

    public CalendarAttributeServiceImpl(CalendarAttributeRepository calendarAttributeRepository) {
        this.calendarAttributeRepository = calendarAttributeRepository;
    }

    /**
     * Save a calendarAttribute.
     *
     * @param calendarAttribute the entity to save
     * @return the persisted entity
     */
    @Override
    public CalendarAttribute save(CalendarAttribute calendarAttribute) {
        log.debug("Request to save CalendarAttribute : {}", calendarAttribute);
        return calendarAttributeRepository.save(calendarAttribute);
    }

    /**
     * Get all the calendarAttributes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<CalendarAttribute> findAll(Pageable pageable) {
        log.debug("Request to get all CalendarAttributes");
        return calendarAttributeRepository.findAll(pageable);
    }


    /**
     * Get one calendarAttribute by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<CalendarAttribute> findOne(String id) {
        log.debug("Request to get CalendarAttribute : {}", id);
        return calendarAttributeRepository.findById(id);
    }

    /**
     * Delete the calendarAttribute by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete CalendarAttribute : {}", id);
        calendarAttributeRepository.deleteById(id);
    }
}
