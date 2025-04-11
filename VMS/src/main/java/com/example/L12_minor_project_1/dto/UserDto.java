package com.example.L12_minor_project_1.dto;

import com.example.L12_minor_project_1.entity.Flat;
import com.example.L12_minor_project_1.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotNull
    @Size(max = 255)
    private String name;
    @NotNull
    @Size(max = 255)
    private String email;
    @Size(min = 10)
    @NotNull
    private String phone;
    @NotNull
    private String idNumber;
    private Role role;
    private String flatNo;
    private AddressDto address;
    private String password;

}
