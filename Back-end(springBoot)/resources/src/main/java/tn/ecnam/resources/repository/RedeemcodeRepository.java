package tn.ecnam.resources.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Reedemcode;
import java.util.Date;
import java.util.List;
@Repository
public interface RedeemcodeRepository extends MongoRepository<Reedemcode,String> {

    Reedemcode findByToken(String token);
    List<Reedemcode> findByCreatedTimeBefore(Date date);

}



