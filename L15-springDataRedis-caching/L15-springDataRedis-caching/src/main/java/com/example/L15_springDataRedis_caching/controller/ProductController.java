package com.example.L15_springDataRedis_caching.controller;

import com.example.L15_springDataRedis_caching.entity.ProductBean;
import com.example.L15_springDataRedis_caching.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/id")
    public ResponseEntity<ProductBean> getProductById(@PathVariable Long id){
        ProductBean productBean = productService.getProductById(id);
        if(productBean==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productBean);
    }






}
