package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ContactController {
                 
	@GetMapping("/contact")
	public String getContactDetails() {
		return "Induiry details are saved to  the DB";
	}
}
