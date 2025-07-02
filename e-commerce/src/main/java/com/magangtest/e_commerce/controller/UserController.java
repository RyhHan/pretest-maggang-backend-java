package com.magangtest.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magangtest.e_commerce.entity.Cart;
import com.magangtest.e_commerce.entity.User;
import com.magangtest.e_commerce.repository.CartRepository;
import com.magangtest.e_commerce.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
        @RequestParam("username") String username,
        @RequestParam("email") String email,
        @RequestParam("password") String password) {

        User user = new User(username, email, password);
        User savedUser = userRepository.save(user);

        Cart cart = new Cart(savedUser); 
        cartRepository.save(cart);

        savedUser.setCart(cart);
        userRepository.save(savedUser); 

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
