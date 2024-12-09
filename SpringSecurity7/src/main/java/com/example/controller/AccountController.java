package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	
	@GetMapping("/myAccounts")
	public String getAccountDetails() {
		return "Welcome to account details from the DB";
	}
	

}
