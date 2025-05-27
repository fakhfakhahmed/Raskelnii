package tn.ecnam.resources.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Comments;
@Repository
public interface CommentRepository extends MongoRepository<Comments,String> {
}
