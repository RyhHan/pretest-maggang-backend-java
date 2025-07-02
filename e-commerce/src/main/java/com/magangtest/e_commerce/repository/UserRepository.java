package com.magangtest.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magangtest.e_commerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}