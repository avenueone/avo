package org.avenue1.avo.service;

import org.avenue1.avo.domain.CalendarAttribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CalendarAttribute.
 */
public interface CalendarAttributeService {

    /**
     * Save a calendarAttribute.
     *
     * @param calendarAttribute the entity to save
     * @return the persisted entity
     */
    CalendarAttribute save(CalendarAttribute calendarAttribute);

    /**
     * Get all the calendarAttributes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CalendarAttribute> findAll(Pageable pageable);


    /**
     * Get the "id" calendarAttribute.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CalendarAttribute> findOne(String id);

    /**
     * Delete the "id" calendarAttribute.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
