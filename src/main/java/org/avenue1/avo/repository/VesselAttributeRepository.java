package org.avenue1.avo.repository;

import org.avenue1.avo.domain.VesselAttribute;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the VesselAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VesselAttributeRepository extends MongoRepository<VesselAttribute, String> {

}
