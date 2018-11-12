package org.avenue1.avo.service;

import org.avenue1.avo.domain.Calendar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Calendar.
 */
public interface CalendarService {

    /**
     * Save a calendar.
     *
     * @param calendar the entity to save
     * @return the persisted entity
     */
    Calendar save(Calendar calendar);

    /**
     * Get all the calendars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Calendar> findAll(Pageable pageable);


    /**
     * Get the "id" calendar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Calendar> findOne(String id);

    /**
     * Delete the "id" calendar.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
