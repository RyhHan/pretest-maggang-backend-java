package com.magangtest.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magangtest.e_commerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
