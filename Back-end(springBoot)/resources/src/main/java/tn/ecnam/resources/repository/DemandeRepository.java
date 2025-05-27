package tn.ecnam.resources.repository;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Demande;
@Repository
public interface DemandeRepository extends MongoRepository <Demande,String> {
}
