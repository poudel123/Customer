package com.capgemini.Customerapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.Customerapp.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
