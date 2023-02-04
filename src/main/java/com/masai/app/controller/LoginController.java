package com.masai.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.app.entity.Customer;
import com.masai.app.repository.CustomerRepository;

@RestController
public class LoginController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/signIn")
    public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(Authentication auth){
        System.out.println(auth.getName());
        Customer customer = customerRepository.findByEmail(auth.getName()).orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
        return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
    }
}

