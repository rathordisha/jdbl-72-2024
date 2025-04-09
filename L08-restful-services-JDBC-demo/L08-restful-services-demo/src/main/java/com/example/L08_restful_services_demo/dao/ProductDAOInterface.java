package com.example.L08_restful_services_demo.dao;

import com.example.L08_restful_services_demo.entity.Product;

public interface ProductDAOInterface {

    Product getById(Long id);

    Product create(Product product);

    Product delete(Long id);

    Product update(Long id, Product product) ;
}
