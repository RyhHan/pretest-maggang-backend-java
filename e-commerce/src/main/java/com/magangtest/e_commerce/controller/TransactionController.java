package com.magangtest.e_commerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magangtest.e_commerce.entity.Cart;
import com.magangtest.e_commerce.entity.CartItem;
import com.magangtest.e_commerce.entity.Transaction;
import com.magangtest.e_commerce.entity.TransactionDetail;
import com.magangtest.e_commerce.repository.CartRepository;
import com.magangtest.e_commerce.repository.ProductRepository;
import com.magangtest.e_commerce.repository.TransactionDetailRepository;
import com.magangtest.e_commerce.repository.TransactionRepository;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<?> processTransaction(@PathVariable Long userId, @RequestParam Long cartId) {

        if (cartId == null) {
            return ResponseEntity.badRequest().body("Cart ID is required.");
        }

        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null || !cart.getUser().getId().equals(userId)) {
            return ResponseEntity.badRequest().body("Invalid cart or user mismatch.");
        }

        Double totalPrice = 0.0;
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice();
        }

        Transaction transaction = new Transaction();
        transaction.setUser(cart.getUser());
        transaction.setTotalPrice(totalPrice);
        transactionRepository.save(transaction);

        for (CartItem cartItem : cartItems) {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setTransaction(transaction);
            transactionDetail.setProduct(cartItem.getProduct());
            transactionDetail.setQuantity(cartItem.getQuantity());
            transactionDetail.setPrice(cartItem.getPrice());
            transactionDetailRepository.save(transactionDetail);
        }

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return ResponseEntity.ok("Transaction processed successfully.");
    }

    @GetMapping("/user/{userId}")
public ResponseEntity<?> getTransactionsByUserId(@PathVariable Long userId) {
    // Mendapatkan semua transaksi milik user yang diberikan
    List<Transaction> transactions = transactionRepository.findByUserId(userId);

    // Map setiap transaksi ke bentuk yang lebih sederhana untuk respon
    List<Map<String, Object>> responseTransactions = transactions.stream()
            .map(transaction -> {
                Map<String, Object> responseTransaction = new HashMap<>();
                responseTransaction.put("id", transaction.getId());
                responseTransaction.put("totalPrice", transaction.getTotalPrice());
                responseTransaction.put("createdAt", transaction.getCreatedAt());

                List<Map<String, Object>> transactionDetails = transaction.getTransactionDetails().stream()
                        .map(transactionDetail -> {
                            Map<String, Object> transactionDetailMap = new HashMap<>();
                            transactionDetailMap.put("productName", transactionDetail.getProduct().getName());
                            transactionDetailMap.put("quantity", transactionDetail.getQuantity());
                            transactionDetailMap.put("price", transactionDetail.getPrice());
                            return transactionDetailMap;
                        })
                        .collect(Collectors.toList());

                responseTransaction.put("transactionDetails", transactionDetails);

                return responseTransaction;
            })
            .collect(Collectors.toList());

    return ResponseEntity.ok(responseTransactions);
}

}
