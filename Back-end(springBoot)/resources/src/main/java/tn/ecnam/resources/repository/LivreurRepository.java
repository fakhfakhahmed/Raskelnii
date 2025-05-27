package tn.ecnam.resources.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Livreur;

import java.util.Optional;
@Repository
public interface LivreurRepository extends MongoRepository<Livreur,String> {
    Optional<Livreur> findByMail(String email) ;
    Optional<Livreur> findByUserName(String username) ;
}
