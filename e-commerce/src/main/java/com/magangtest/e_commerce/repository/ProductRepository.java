package com.magangtest.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magangtest.e_commerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
