package com.example.L12_minor_project_1.util;

import com.example.L12_minor_project_1.entity.Visit;
import com.example.L12_minor_project_1.enums.VisitStatus;
import com.example.L12_minor_project_1.repo.VisitRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;


@Configuration
public class VisitExpireScheduledTask {

    private Logger logger= LoggerFactory.getLogger(VisitExpireScheduledTask.class);

    @Autowired
    private VisitRepo visitRepo;

    @Scheduled(fixedDelay = 50000)
    public void markVisitAsExpired(){
        logger.info("Marking visit as expired");

//30 be kept in property file as dynamic
        Date date=new Date();
        date.setMinutes(date.getMinutes()-30);

        List<Visit> visitList=visitRepo.findByStatusAndCreatedDateLessThanEqual(VisitStatus.WAITING,date);
        for(Visit visit: visitList){
            visit.setStatus(VisitStatus.EXPIRE);
        }
        visitRepo.saveAll(visitList);
    }
}
