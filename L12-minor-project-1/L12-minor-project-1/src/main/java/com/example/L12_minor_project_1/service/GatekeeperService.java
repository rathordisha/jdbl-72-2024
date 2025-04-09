package com.example.L12_minor_project_1.service;

import com.example.L12_minor_project_1.dto.AddressDto;
import com.example.L12_minor_project_1.dto.VisitDto;
import com.example.L12_minor_project_1.dto.VisitorDto;
import com.example.L12_minor_project_1.entity.Address;
import com.example.L12_minor_project_1.entity.Flat;
import com.example.L12_minor_project_1.entity.Visit;
import com.example.L12_minor_project_1.entity.Visitor;
import com.example.L12_minor_project_1.enums.VisitStatus;
import com.example.L12_minor_project_1.exceptions_custom.BadRequestException;
import com.example.L12_minor_project_1.exceptions_custom.NotFoundException;
import com.example.L12_minor_project_1.repo.FlatRepo;
import com.example.L12_minor_project_1.repo.VisitRepo;
import com.example.L12_minor_project_1.repo.VisitorRepo;
import com.example.L12_minor_project_1.util.CommonUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class GatekeeperService {

    @Autowired
    private VisitorRepo visitorRepo;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private FlatRepo flatRepo;

    //bean required to interact with redis server
    @Autowired
    private RedisTemplate<String, VisitorDto> redisTemplate;

    public VisitorDto getVisitor(String idNumber){
    // cache logic: Key: some_prefix:id = visitor:{idNumber},Value: object visitorDto
        //need to generate cache key
        String key="visitor:"+idNumber;
        //first try to fetch data from redis-template(cache) using key,
        VisitorDto visitorDto= redisTemplate.opsForValue().get(key);
        //if not present in cache then fetch from DB
        if(visitorDto==null){
            Visitor visitor =visitorRepo.findByIdNumber(idNumber);
            if(visitor !=null){
                visitorDto= VisitorDto.builder()
                        .phone(visitor.getPhone())
                        .email(visitor.getEmail())
                        .name(visitor.getName())
                        .idNumber(visitor.getIdNumber())
                        //  .address(visitor.getAddress())
                        .build();
            }
            //after fetching from db, populate visitorDto in cache against its key
            redisTemplate.opsForValue().set(key,visitorDto,24, TimeUnit.HOURS);
        }
        return visitorDto;
    }

    public Long createVisitor(VisitorDto visitorDto) {
        AddressDto addressDto= visitorDto.getAddress();
        Address address=commonUtil.convertAddressToDTO(addressDto);

        Visitor visitor= Visitor.builder()
                .address(address)
                .name(visitorDto.getName())
                .email(visitorDto.getEmail())
                .phone(visitorDto.getPhone())
                .idNumber(visitorDto.getIdNumber())
                .build();

        visitor=visitorRepo.save(visitor);
        return visitor.getId();
    }

    public Long createVisit(VisitDto visitDto) {
        Flat flat=flatRepo.findByNumber(visitDto.getFlatNumber());
        Visitor visitor=visitorRepo.findByIdNumber(visitDto.getIdNumber());
        Visit visit = Visit.builder()
                .flat(flat)
                .purpose(visitDto.getPurpose())
                .imageUrl(visitDto.getImageUrl())
                .noOfPeople(visitDto.getNoOfPeople())
                .visitor(visitor)
                .status(VisitStatus.WAITING)
                .build();
        visit=visitRepo.save(visit);
        return visit.getId();
    }

    @Transactional
    public String markEntry(Long id){
        Optional<Visit> optionalVisit=visitRepo.findById(id);
        if(optionalVisit.isEmpty()){
            throw new NotFoundException("------- visit not found ---------");
        }
        Visit visit=optionalVisit.get();

        if(VisitStatus.APPROVED.equals(visit.getStatus())){
            visit.setInTime(new Date());
            //visitRepo.save(visit); without transactional
        } else{
            throw new BadRequestException(" ---------- Invalid state transition ");
        }
        return "Done";
    }

    @Transactional
    public String markExit(Long id) {
        Optional<Visit> optionalVisit=visitRepo.findById(id);
        if(optionalVisit.isEmpty()){
            throw new NotFoundException("visit not found");
        }
        Visit visit=optionalVisit.get();

        if(VisitStatus.APPROVED.equals(visit.getStatus()) && visit.getInTime()!=null){
            visit.setOutTime(new Date());
            visit.setStatus(VisitStatus.COMPLETED);
        } else{
            throw new BadRequestException("Invalid state transition ");
        }
        return "Done";
    }
}
