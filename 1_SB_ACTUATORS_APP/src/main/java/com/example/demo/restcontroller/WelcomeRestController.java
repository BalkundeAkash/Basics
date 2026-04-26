package com.example.demo.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

@RestController
public class WelcomeRestController {

	@GetMapping("/welcome")
	public String welcomeMsg() {
		return "WelCome to Akash Family..!!";
	}

}
