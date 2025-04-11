package com.example.L12_minor_project_1.repo;

import com.example.L12_minor_project_1.entity.Visit;
import com.example.L12_minor_project_1.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepo extends JpaRepository<Visitor,Long> {

    Visitor findByIdNumber(String idNumber);
}
