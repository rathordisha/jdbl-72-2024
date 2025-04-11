package com.example.L12_minor_project_1.dto;

import com.example.L12_minor_project_1.entity.Visit;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllPendingVisitsDTO {
    private List<VisitDto> visits;

    private Long totalRows;

    private Integer totalPages;

}
