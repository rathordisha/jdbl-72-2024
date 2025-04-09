package com.example.L10_JPA_And_Transactions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name= "empName",nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToOne //while fetching from db , this address will be fetched according to employee
    private Address address;
}
