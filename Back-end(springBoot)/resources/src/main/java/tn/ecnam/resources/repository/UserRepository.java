package tn.ecnam.resources.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Demande;
import tn.ecnam.resources.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<User,String> {

    Optional<User> findByMail(String email) ;
    Optional<User> findByUserName(String username) ;
    @Query("{'demandes.id': ?0}")
    User findUserByDemandeId(String demandeId);
    List<User> findByInterestingEventIds(String blogId);
    List<User> findByLikedBlogsIds(String blogId);

}
