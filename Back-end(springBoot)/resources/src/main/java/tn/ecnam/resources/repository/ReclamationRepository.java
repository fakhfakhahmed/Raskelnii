package tn.ecnam.resources.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Reclamation;

@Repository
public interface ReclamationRepository extends MongoRepository<Reclamation,String> {
}
