package org.avenue1.avo.repository;

import org.avenue1.avo.domain.CalendarAttribute;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CalendarAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarAttributeRepository extends MongoRepository<CalendarAttribute, String> {

}
