package com.magangtest.e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magangtest.e_commerce.entity.TransactionDetail;
import com.magangtest.e_commerce.repository.TransactionDetailRepository;

@RestController
@RequestMapping("/transaction-details")
public class TransactionDetailController {

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @GetMapping
    public List<TransactionDetail> getAllTransactionDetails() {
        return transactionDetailRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<TransactionDetail> addTransactionDetail(@RequestBody TransactionDetail transactionDetail) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDetailRepository.save(transactionDetail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionDetail(@PathVariable Long id) {
        transactionDetailRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
