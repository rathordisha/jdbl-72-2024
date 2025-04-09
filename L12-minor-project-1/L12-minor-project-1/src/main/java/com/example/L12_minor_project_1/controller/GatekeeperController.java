package com.example.L12_minor_project_1.controller;

import com.example.L12_minor_project_1.dto.VisitDto;
import com.example.L12_minor_project_1.dto.VisitorDto;
import com.example.L12_minor_project_1.entity.Visit;
import com.example.L12_minor_project_1.service.GatekeeperService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/gatekeeper")
public class GatekeeperController {

    @Autowired
    private GatekeeperService gatekeeperService;

    @GetMapping("/getVisitor")
    ResponseEntity<VisitorDto> getVisitorByIdNumber(@RequestParam String idNumber){
      VisitorDto visitorDto= gatekeeperService.getVisitor(idNumber);
      if(visitorDto==null){
          return ResponseEntity.notFound().build();
      }
        return ResponseEntity.ok(visitorDto);
    }

    @PostMapping("/createVisitor")
    ResponseEntity<Long> createVisitor(@RequestBody @Valid VisitorDto visitorDto){
        Long id = gatekeeperService.createVisitor(visitorDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/createVisit")
    ResponseEntity<Long> createVisit(@RequestBody @Valid VisitDto visitDto){
        Long id = gatekeeperService.createVisit(visitDto);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/markEntry/{id}")
    ResponseEntity<String> markEntry(@PathVariable Long id){//visit id
        return ResponseEntity.ok(gatekeeperService.markEntry(id));
    }
    @PutMapping("/markExit/{id}")
    ResponseEntity<String> markExit(@PathVariable Long id){
    return ResponseEntity.ok(gatekeeperService.markExit(id));
    }

    @PostMapping("/image-upload")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file){
        log.info("---------- in cnt");
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded!");
        }

        String fileName= UUID.randomUUID()+"_"+file.getOriginalFilename();
        log.info("-------- file = "+fileName);
        String uploadPath="C:/Users/Disha/Downloads/images/"+fileName;
        String publicURL="http://localhost:8084/content/"+fileName;
       // String publicURL="/content/"+fileName;
            try {
                file.transferTo(new File(uploadPath));
            } catch (IOException e) {
                log.info("exception while uploading image :{}", e.getMessage(),e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed!");
            }
        return ResponseEntity.ok(publicURL);
    }
}
