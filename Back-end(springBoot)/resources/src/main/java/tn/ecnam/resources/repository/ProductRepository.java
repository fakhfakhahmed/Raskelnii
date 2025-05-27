package tn.ecnam.resources.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

}
