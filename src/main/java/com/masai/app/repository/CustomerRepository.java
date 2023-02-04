package com.masai.app.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.app.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
}

