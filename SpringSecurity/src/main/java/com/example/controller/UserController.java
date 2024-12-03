package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
@RestController
public class UserController {
	
    @Autowired
  private CustomerRepository customerRepository;
    
    //in order to hash(means bcrypt) the password  before we try to store the password  into database we need help of  PasswordEncoder bean.
    @Autowired
   private  PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
       public ResponseEntity<String> registerUser(@RequestBody Customer customer){
    	   try {
    		      String hashpwd=passwordEncoder.encode(customer.getPwd());
    		      customer.setPwd(hashpwd);
    		     Customer savdCustomer= customerRepository.save(customer);
    		     
    		     if (savdCustomer.getId()>0) {
					return ResponseEntity.status(HttpStatus.CREATED).body("Given user details are successfully register");
				}
    		     else {
 					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sr registration failed");

    		     }
    	   }
    	   catch(Exception e) {
    		   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Exception occured "+ e.getMessage());
    	   }
       }
}
