package com.example.L08_restful_services_demo.dao;

import com.example.L08_restful_services_demo.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductDAO implements ProductDAOInterface{

    private Map<Long,Product> dataStore = new HashMap<>();
    private AtomicLong nextId=new AtomicLong(1l);

    public Product getById(Long id){
        return dataStore.get(id);
    }

    public Product create(Product product){
        product.setId(nextId.getAndIncrement());
        dataStore.put(product.getId(),product);
        return product;
    }

    public Product delete(Long id) {
        Product existingProduct = dataStore.get(id);
        if(existingProduct==null){
            return null;
        }
        dataStore.remove(id);
        return existingProduct;
    }

    public Product update(Long id,Product product) {
        Product existingProduct = dataStore.get(id);//getting existing product
        if(existingProduct==null){
            return null;
        }
        //setting existing product
        existingProduct.setName(product.getName());
        existingProduct.setCost(product.getCost());
        return existingProduct;
    }
}
