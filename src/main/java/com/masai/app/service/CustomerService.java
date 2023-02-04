package com.masai.app.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.app.entity.Customer;
import com.masai.app.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        return customer.orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}

