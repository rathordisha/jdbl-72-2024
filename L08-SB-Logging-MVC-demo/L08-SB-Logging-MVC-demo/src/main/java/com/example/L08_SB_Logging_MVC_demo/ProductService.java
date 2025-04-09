package com.example.L07_spring_boot_first;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private Map<Long,ProductBean> productMap;

    @PostConstruct
    public void initMethod(){
     productMap = new HashMap<>();
     productMap.put(1L,new ProductBean(1L,"laptop",50000.0));
     productMap.put(2L,new ProductBean(2L,"Mouse",1000.0));
     productMap.put(3L,new ProductBean(3L,"Keyboard",4000.0));
 }

    public ProductBean getProductById(Long id){
        return productMap.get(id);
    }
}
