package com.example.L12_minor_project_1.controller;

import com.example.L12_minor_project_1.dto.AllPendingVisitsDTO;
import com.example.L12_minor_project_1.dto.VisitDto;
import com.example.L12_minor_project_1.dto.VisitorDto;
import com.example.L12_minor_project_1.entity.EndUser;
import com.example.L12_minor_project_1.enums.VisitStatus;
import com.example.L12_minor_project_1.service.ResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    //should return a proper json here
    //approve or waiting(->reject) in the same api so we need visit status
    @PutMapping("/actOnVisit/{id}")
    public ResponseEntity<String> actOnVisit(@PathVariable Long id, @RequestParam VisitStatus visitStatus){//visit_id
        log.info("---------- in cnt -------------");
        return ResponseEntity.ok(residentService.updateVisit(id,visitStatus));
    }

    //getting pending visits for that particular flat
//    @GetMapping("/pendingVisits")
//    public ResponseEntity<List<VisitDto>> getPendingVisits(@RequestHeader Long userId){
//      return ResponseEntity.ok(residentService.getPendingVisits(userId));
//    }

    //getting pending visits for the user who is logged in: after security config implementation,
    // earlier we were providing user id in request header
    //with @AuthenticationPrincipal we access details of logged-in user and pass get userid
    @GetMapping("/pendingVisits")
    public ResponseEntity<List<VisitDto>> getPendingVisits(@AuthenticationPrincipal EndUser user){
        return ResponseEntity.ok(residentService.getPendingVisits(user.getId()));
    }

    //getting page wise pending visits for that particular flat
    //1(pageNo) of 12(pageSize) of 124 entries
//    @GetMapping("/page-pendingVisits")
//    public ResponseEntity<List<VisitDto>> getPendingVisitsByPage(@RequestHeader Long userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
//        return ResponseEntity.ok(residentService.getPendingVisitsByPage(userId,pageNo,pageSize));
//    }

    //using dto
    @GetMapping("/page-pendingVisits")
    public ResponseEntity<AllPendingVisitsDTO> getPendingVisitsByPage(@RequestHeader Long userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        return ResponseEntity.ok(residentService.getPendingVisitsByPage(userId,pageNo,pageSize));
    }

}
