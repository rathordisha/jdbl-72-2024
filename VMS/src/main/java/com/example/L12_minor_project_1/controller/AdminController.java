package com.example.L12_minor_project_1.controller;

import com.example.L12_minor_project_1.dto.AddressDto;
import com.example.L12_minor_project_1.dto.UserDto;
import com.example.L12_minor_project_1.enums.Role;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.L12_minor_project_1.service.AdminService;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

   // private Logger LOGGER= LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PostMapping("/createUser")
    ResponseEntity<Long> createUser(@RequestBody @Valid UserDto userDto){
        Long id= adminService.createUser(userDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/create-bulk-user")
    ResponseEntity<List<String>> createBulkUser(@RequestParam MultipartFile file){
        log.info("flie --------- "+file.getOriginalFilename());
        String currentIdNumber="";
        List<String> response = new ArrayList<>();
        try {
            BufferedReader fileReader=new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser=new CSVParser(fileReader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase());
            List<CSVRecord> csvRecordsLst= csvParser.getRecords();

            for(CSVRecord csvRecord:csvRecordsLst){
                try{
                    AddressDto addressDto=AddressDto.builder()
                            .line1(csvRecord.get("line1"))
                            .line2(csvRecord.get("line2"))
                            .city(csvRecord.get("city"))
                            .pincode(csvRecord.get("pincode"))
                            .build();
                    //column name sequence doesn't matter
                    UserDto userDto= UserDto.builder()
                            .name(csvRecord.get("name"))
                            .email(csvRecord.get("email"))
                            .phone(csvRecord.get("phone"))
                            .flatNo(csvRecord.get("flatNo"))
                            .role(Role.valueOf(csvRecord.get("role")))
                            .idNumber(csvRecord.get("idNumber"))
                            .address(addressDto)
                            .build();
                    currentIdNumber=userDto.getIdNumber();
                    log.info("--------- calling service");
                    Long userId= adminService.createUser(userDto);
                    response.add("Created User "+userDto.getName()+" with id "+userId);

                } catch (RuntimeException e){
                        response.add("got exception while creating id "+currentIdNumber);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(response);
    }
}





