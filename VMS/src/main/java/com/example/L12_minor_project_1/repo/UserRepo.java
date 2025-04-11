package com.example.L12_minor_project_1.repo;

import com.example.L12_minor_project_1.entity.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<EndUser,Long> {

    EndUser findByEmail(String username);
}
