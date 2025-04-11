package com.example.L12_minor_project_1.service;

import com.example.L12_minor_project_1.dto.AllPendingVisitsDTO;
import com.example.L12_minor_project_1.dto.VisitDto;
import com.example.L12_minor_project_1.entity.EndUser;
import com.example.L12_minor_project_1.entity.Flat;
import com.example.L12_minor_project_1.entity.Visit;
import com.example.L12_minor_project_1.entity.Visitor;
import com.example.L12_minor_project_1.enums.VisitStatus;
import com.example.L12_minor_project_1.exceptions_custom.BadRequestException;
import com.example.L12_minor_project_1.exceptions_custom.NotFoundException;
import com.example.L12_minor_project_1.repo.VisitRepo;
import com.example.L12_minor_project_1.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ResidentService {

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private UserRepo userRepo;

    //private static final Logger LOG =   LoggerFactory.getLogger(ResidentService.class);


    //update the visit status to either approve or reject from waiting
    public String updateVisit(Long id, VisitStatus visitStatus) {
//        if(visitStatus!=VisitStatus.APPROVED && visitStatus!=VisitStatus.REJECTED){
//            //stop here only
//            throw new BadRequestException("Invalid state transition");
//        }
        Visit visit = visitRepo.getReferenceById(id);
        if(visit==null){
            throw new NotFoundException("------- Visit Not Found ---------");
        }
        if(VisitStatus.WAITING.equals(visit.getStatus())){
            log.info("inside if condition: waiting");
            visit.setStatus(visitStatus);
            visitRepo.save(visit);
        } else{
            //throw some exception
            throw new BadRequestException("Invalid state transition");
        }
        return "Done";
    }

    //getting waiting visits by userId for that flat
    public List<VisitDto> getPendingVisits(Long userId){
        EndUser endUser=userRepo.findById(userId).get();
        Flat flat=endUser.getFlat();

        List<Visit> visitList=visitRepo.findByStatusAndFlat(VisitStatus.WAITING,flat);
        List<VisitDto> visitDtoList=new ArrayList<>();

        for(Visit visit:visitList){
            Visitor visitor=visit.getVisitor();

            VisitDto visitDto=VisitDto.builder()
                    .flatNumber(flat.getNumber())
                    .noOfPeople(visit.getNoOfPeople())
                    .purpose(visit.getPurpose())
                    .imageUrl(visit.getImageUrl())
                    .idNumber(visitor.getIdNumber())
                    .visitorName(visitor.getName())
                    .visitorPhone(visitor.getPhone())
                    .status(visit.getStatus())
                    .build();
            visitDtoList.add(visitDto);
        }
        return visitDtoList;
    }


    public AllPendingVisitsDTO getPendingVisitsByPage(Long userId, Integer pageNo, Integer pageSize){
        //replacing List<VisitDto> with allPendingVisitsDTO
        AllPendingVisitsDTO allPendingVisitsDTO=new AllPendingVisitsDTO();
        //first fetch user then flat
        EndUser endUser=userRepo.findById(userId).get();
        Flat flat=endUser.getFlat();

        Pageable pageable= Pageable.ofSize(pageSize).withPage(pageNo);

        Page<Visit> visitPage= visitRepo.findByStatusAndFlat(VisitStatus.WAITING,flat, pageable);
        List<VisitDto> visitDtoList=new ArrayList<>();
        List<Visit> visitList=visitPage.stream().toList();
        for(Visit visit:visitList){
            Visitor visitor=visit.getVisitor();

            VisitDto visitDto=VisitDto.builder()
                    .flatNumber(flat.getNumber())
                    .noOfPeople(visit.getNoOfPeople())
                    .purpose(visit.getPurpose())
                    .imageUrl(visit.getImageUrl())
                    .idNumber(visitor.getIdNumber())
                    .visitorName(visitor.getName())
                    .visitorPhone(visitor.getPhone())
                    .status(visit.getStatus())
                    .build();
            visitDtoList.add(visitDto);

            //creating a dto to get total pages and total elements
            allPendingVisitsDTO.setVisits(visitDtoList);
            allPendingVisitsDTO.setTotalPages(visitPage.getTotalPages());
            allPendingVisitsDTO.setTotalRows(visitPage.getTotalElements());
        }
       // return visitDtoList;
        return allPendingVisitsDTO;
    }
}
