package com.example.L12_minor_project_1.dto;

import com.example.L12_minor_project_1.entity.Flat;
import com.example.L12_minor_project_1.entity.Visitor;
import com.example.L12_minor_project_1.enums.VisitStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDto {
    //these fields are required when returning a visit
    private VisitStatus status;
    private Date inTime;//visitor approved then entry time
    private Date outTime;

    //below fields are required when creating a visit
    @NotNull
    @Size(max = 255)
    private String purpose;
    @NotNull
    private Integer noOfPeople;
    @Size(max = 255)
    private String imageUrl;
    @NotNull
    private String idNumber;
    @NotNull
    private String flatNumber;

    private String visitorName;
    private String visitorPhone;
}
