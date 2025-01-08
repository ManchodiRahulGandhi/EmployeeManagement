package com.nt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    com.nt.model.User findByEmail(String email);

	com.nt.model.User save(com.nt.model.User user);
}