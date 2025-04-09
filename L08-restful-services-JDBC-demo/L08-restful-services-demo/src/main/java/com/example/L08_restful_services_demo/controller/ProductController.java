package com.example.L08_restful_services_demo.controller;

import com.example.L08_restful_services_demo.entity.Product;
import com.example.L08_restful_services_demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/RESTproduct")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        if(id<1){
            log.error("HTTP err code 400");
            return ResponseEntity.badRequest().build();
        }
       Product product= productService.getById(id);
        if(product==null){
            log.error("HTTP err code 404");
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.ok(product);
    }

    //201 response code will create the product only, here we require uri
//    @PostMapping("/post")
//    public ResponseEntity<Void> createProduct(@RequestBody Product product){
//        product=productService.create(product);
//        URI uri= null;
//        try {
//            uri = new URI("http://localhost:8083/restproduct/post"+product.getId());
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        return ResponseEntity.created(uri).build();
//    }

    //200 response code will return the product created
    //no need of uri or build
    //mostly used
    @PostMapping("/post")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
       // log.info("in post request");
        product = productService.create(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
       Product deleteProduct = productService.delete(id);
       if(deleteProduct==null){
           return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok(deleteProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        Product updateProduct = productService.update(id,product);
        if(updateProduct==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateProduct);
    }
}


