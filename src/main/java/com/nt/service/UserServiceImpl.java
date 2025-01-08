package com.nt.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.dto.UserRegistrationDto;
import com.nt.model.Role;
import com.nt.model.User;
import com.nt.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
   /*
     public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
                }
    */
    public User save(UserRegistrationDto registrationDto) {
    User user1 = new User();
    user1.setFirstName(registrationDto.getFirstName());
    user1.setEmail(registrationDto.getEmail());
    user1.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
    // Set roles using setter (assuming roles is a List<Role>)
    user1.setRoles(Arrays.asList(new Role("ROLE_USER")));
    //The method setRoles(Arrays.asList(new Role("ROLE_USER"))) is undefined for the type User
    return userRepository.save(user1);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user by email
        User user = userRepository.findByEmail(username);
        
        // If user is not found, throw UsernameNotFoundException
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        
        // Map the roles to authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), 
                user.getPassword(), 
                mapRolesToAuthorities(null)
                //mapRolesToAuthorities(user.getRoles())
        );
    }

    // Method to map roles to authorities (GrantedAuthority)
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName())) // Map Role to SimpleGrantedAuthority
                    .collect(Collectors.toList());
    }

}