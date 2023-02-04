package com.masai.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masai.app.entity.Customer;

@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return User.withUsername(customer.getEmail())
                   .password(customer.getPassword())
                   .roles(customer.getRole())
                   .build();
    }
}

