package com.example.L12_minor_project_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //disabling csrf for post requests to work as csrf is implemented auto
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                //added for authorization i.e. admin can only use admin functionalities, he cant perform GK roles..
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/admin/**").hasAnyAuthority("ADMIN");
                    auth.requestMatchers("/gatekeeper/**").hasAnyAuthority("GATEKEEPER");
                    auth.requestMatchers("/resident/**").hasAnyAuthority("RESIDENT");
                    // public url to access, use every functionalities
                    auth.requestMatchers("/public/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
