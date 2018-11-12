package org.avenue1.avo.repository;

import org.avenue1.avo.domain.Vessel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Vessel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VesselRepository extends MongoRepository<Vessel, String> {
    @Query("{}")
    Page<Vessel> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Vessel> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Vessel> findOneWithEagerRelationships(String id);

}
