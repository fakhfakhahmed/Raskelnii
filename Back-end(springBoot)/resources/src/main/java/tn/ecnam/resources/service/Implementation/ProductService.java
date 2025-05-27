package tn.ecnam.resources.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Product;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.ProductRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.IProductService;
import tn.ecnam.resources.service.IUserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository Pr;
    @Autowired
    private IUserService us;
    @Autowired
    private UserRepository ur;
    @Override
    public Product AddProduct(Product product){
            return  Pr.save(product);
    }

    @Override
    @Transactional
    public void AddProductToUser(Product product) throws Exception {
        User user = us.LoggedInUser();
        Set<Product> products = user.getProducts();
        products.add(product);
        user.setProducts(products);
        ur.save(user);
    }

    @Override
    public Product UpdateProduct(Product product) {
        return Pr.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
            Pr.delete(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>)  Pr.findAll();
    }

    @Override
    public Product getProduct(String ProductId) {
        return Pr.findById(ProductId).orElse(null);
    }

    ;
}
