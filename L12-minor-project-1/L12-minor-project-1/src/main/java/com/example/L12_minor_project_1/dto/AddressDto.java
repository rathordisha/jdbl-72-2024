package com.example.L12_minor_project_1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    @NotNull
    private Long id;

    private String line1;
    private String line2;

    @NotNull
    private String city;
    private String pincode;

}
