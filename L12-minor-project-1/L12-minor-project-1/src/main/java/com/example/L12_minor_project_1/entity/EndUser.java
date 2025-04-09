package com.example.L12_minor_project_1.entity;

import com.example.L12_minor_project_1.enums.Role;
import com.example.L12_minor_project_1.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    private String phone;

    @Column(nullable = false,unique = true)
    private String idNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column
    private String password;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date updatedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList=new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(role.name()));
        return grantedAuthorityList;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
     public boolean isAccountNonExpired() {
        return true;
    }

    @Override
     public boolean isAccountNonLocked() {
        return true;
    }

    @Override
     public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
