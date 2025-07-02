package com.magangtest.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magangtest.e_commerce.entity.Cart;
import com.magangtest.e_commerce.entity.CartItem;
import com.magangtest.e_commerce.entity.Product;
import com.magangtest.e_commerce.repository.CartItemRepository;
import com.magangtest.e_commerce.repository.CartRepository;
import com.magangtest.e_commerce.repository.ProductRepository;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(value = "/{cartId}/items", consumes = "multipart/form-data")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable Long cartId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        CartItem cartItem = new CartItem(product, quantity);
        cartItem.setCart(cart);
        cartItem.setPrice(product.getPrice() * quantity);

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return ResponseEntity.ok(savedCartItem);
    }

}
