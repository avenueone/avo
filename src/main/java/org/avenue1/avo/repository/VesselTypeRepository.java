package org.avenue1.avo.repository;

import org.avenue1.avo.domain.VesselType;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the VesselType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VesselTypeRepository extends MongoRepository<VesselType, String> {

}
