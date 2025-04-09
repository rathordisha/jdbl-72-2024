package com.example.L12_minor_project_1.service;

import com.example.L12_minor_project_1.entity.EndUser;
import com.example.L12_minor_project_1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DBUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        EndUser user=userRepo.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("No user with username "+username);
        }
        return user;
    }
}
