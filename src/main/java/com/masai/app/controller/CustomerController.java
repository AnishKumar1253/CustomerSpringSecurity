package com.masai.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.app.entity.Customer;
import com.masai.app.service.CustomerService;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public Customer registerCustomer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}

