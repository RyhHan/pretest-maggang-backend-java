package com.magangtest.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magangtest.e_commerce.entity.TransactionDetail;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
}
