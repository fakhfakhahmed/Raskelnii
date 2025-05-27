package tn.ecnam.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.ecnam.resources.entity.Product;
import tn.ecnam.resources.service.IProductService;

import java.util.List;

@RestController
@RequestMapping("/Product")
public class ProductController {
    @Autowired
    IProductService Ps;

    @PostMapping("/add-product")
    public Product addProduct(@RequestBody Product product) throws Exception {
        Product p = Ps.AddProduct(product);
        Ps.AddProductToUser(p);
        return p;
    }
    @PostMapping("/update-product")
    public Product UpdateProduct(@RequestParam("ProductId") String productId, @RequestBody Product product){
        product.setId(productId);
        return Ps.UpdateProduct(product);
    }
    @DeleteMapping("/delete-product/{productId}")
    public void deleteProduct(@PathVariable("productId") String productId) {
        Product product= Ps.getProduct(productId);
        Ps.deleteProduct(product);
    }
    @GetMapping("/get-all")
    public List<Product> getAll(){
        return Ps.getAllProducts();
    }
    @GetMapping("/{ProductId}")
    public Product getProduct(@PathVariable("ProductId") String ProductId){
        return  Ps.getProduct(ProductId);
    }
}
