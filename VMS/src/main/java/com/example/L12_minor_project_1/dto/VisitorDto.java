package com.example.L12_minor_project_1.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VisitorDto implements Serializable {

    private static final long serialVersionUID= 12l;

    private String name;

    private String email;

    @Size(min = 10)
    @NotNull
    @Column(nullable = false)
    private String phone;

    private String idNumber;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private AddressDto address;
}
