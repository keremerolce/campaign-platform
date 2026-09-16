package com.keremerol.campaign_platform.repository;

import com.keremerol.campaign_platform.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}