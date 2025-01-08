package com.nt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import com.nt.dto.UserRegistrationDto;
import com.nt.model.User;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}