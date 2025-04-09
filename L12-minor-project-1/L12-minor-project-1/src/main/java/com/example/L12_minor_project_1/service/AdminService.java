package com.example.L12_minor_project_1.service;

import com.example.L12_minor_project_1.dto.AddressDto;
import com.example.L12_minor_project_1.dto.UserDto;
import com.example.L12_minor_project_1.entity.Address;
import com.example.L12_minor_project_1.entity.EndUser;
import com.example.L12_minor_project_1.entity.Flat;
import com.example.L12_minor_project_1.enums.UserStatus;
import com.example.L12_minor_project_1.repo.FlatRepo;
import com.example.L12_minor_project_1.util.CommonUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.L12_minor_project_1.repo.UserRepo;

@Slf4j
@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long createUser(UserDto userDto){

        AddressDto addressDto= userDto.getAddress();
        Address address=commonUtil.convertAddressToDTO(addressDto);

        Flat flat=null;
        if(userDto.getFlatNo()!=null){
        // set value of this flat
            flat = flatRepo.findByNumber(userDto.getFlatNo());
            if(flat==null){
                throw new EntityNotFoundException("Flat with number " + userDto.getFlatNo() + " not found.");
            }
        }
        log.info("---------- in service createuser");
        EndUser user= EndUser.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .idNumber(userDto.getIdNumber())
                .role(userDto.getRole())
                .flat(flat)
                .userStatus(UserStatus.ACTIVE)
                .address(address)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
log.info("calling save -------- ");
        user=userRepo.save(user);
        return user.getId();
    }
}
