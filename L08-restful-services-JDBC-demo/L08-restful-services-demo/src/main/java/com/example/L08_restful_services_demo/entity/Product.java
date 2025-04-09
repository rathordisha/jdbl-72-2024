package com.example.L08_restful_services_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private  Long id;
    private String name;
    private Double cost;


    //   private Integer errCode;
  //  private String errMsg;
}
