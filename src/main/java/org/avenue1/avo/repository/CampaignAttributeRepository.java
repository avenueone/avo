package org.avenue1.avo.repository;

import org.avenue1.avo.domain.CampaignAttribute;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CampaignAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignAttributeRepository extends MongoRepository<CampaignAttribute, String> {

}
