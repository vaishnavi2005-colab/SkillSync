package com.example.skillsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.skillsync.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
