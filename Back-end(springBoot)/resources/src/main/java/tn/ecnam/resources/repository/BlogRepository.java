package tn.ecnam.resources.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.User;

import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<Blog,String> {
}
