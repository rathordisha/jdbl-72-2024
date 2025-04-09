package com.example.L12_minor_project_1.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true,nullable = false)
    private String idNumber;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Address address;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date updatedDate;
}
