package tn.ecnam.resources.service;

import tn.ecnam.resources.entity.Product;

import java.util.List;

public interface IProductService {
    Product AddProduct(Product product);

    void AddProductToUser(Product product) throws Exception;
    Product UpdateProduct(Product product);
    void deleteProduct(Product product);
    List<Product>getAllProducts();
    Product getProduct(String ProductId);

}
