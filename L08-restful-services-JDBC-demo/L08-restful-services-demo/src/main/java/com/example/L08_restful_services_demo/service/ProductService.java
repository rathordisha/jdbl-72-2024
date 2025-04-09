package com.example.L08_restful_services_demo.service;

import com.example.L08_restful_services_demo.dao.ProductDAO;
import com.example.L08_restful_services_demo.dao.ProductDAOInterface;
import com.example.L08_restful_services_demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    //private ProductDAO productDAO;

    @Autowired
    @Qualifier("productDAOWithDb")
    private ProductDAOInterface productDAOService;//provide new jdbc implementation

    public Product getById(Long id){
        return productDAOService.getById(id);
    }

    public Product create(Product product){
        return productDAOService.create(product);
    }

    public Product delete(Long id) {
        return productDAOService.delete(id);
    }

    public Product update(Long id, Product product) {
        return productDAOService.update(id,product);
    }
}
