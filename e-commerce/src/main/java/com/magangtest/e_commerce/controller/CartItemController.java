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

import com.magangtest.e_commerce.entity.CartItem;
import com.magangtest.e_commerce.repository.CartItemRepository;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @GetMapping("/user/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByUser(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cartItems);
    }

    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemRepository.save(cartItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
