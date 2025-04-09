package com.example.L07_spring_boot_first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAppInfo(){
        return "hello from rest controller";
    }


    @GetMapping("/hello")
    public String getHello(){
        return "hi from thread - "+Thread.currentThread().getName();
    }

    @GetMapping("/product/{id}")
    public ProductBean getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }
}
