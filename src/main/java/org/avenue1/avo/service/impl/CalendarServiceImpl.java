package org.avenue1.avo.service.impl;

import org.avenue1.avo.service.CalendarService;
import org.avenue1.avo.domain.Calendar;
import org.avenue1.avo.repository.CalendarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Calendar.
 */
@Service
public class CalendarServiceImpl implements CalendarService {

    private final Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);

    private final CalendarRepository calendarRepository;

    public CalendarServiceImpl(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    /**
     * Save a calendar.
     *
     * @param calendar the entity to save
     * @return the persisted entity
     */
    @Override
    public Calendar save(Calendar calendar) {
        log.debug("Request to save Calendar : {}", calendar);
        return calendarRepository.save(calendar);
    }

    /**
     * Get all the calendars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Calendar> findAll(Pageable pageable) {
        log.debug("Request to get all Calendars");
        return calendarRepository.findAll(pageable);
    }


    /**
     * Get one calendar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Calendar> findOne(String id) {
        log.debug("Request to get Calendar : {}", id);
        return calendarRepository.findById(id);
    }

    /**
     * Delete the calendar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Calendar : {}", id);
        calendarRepository.deleteById(id);
    }
}
