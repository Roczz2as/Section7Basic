package com.example.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	// here we are declearing abstract method
	// q -> you may question  where is implmentation logic findByEmail
	// we dont  have to implmentation logic , at run runtime spring framework generate required logic at runtime to etch the 
	// customer record based on the email
	
	 

	Optional<Customer> findByEmail(String username);

}
