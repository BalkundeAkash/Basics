package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {

	@Autowired
	private BankService bankService;
	
	@PostMapping("/transfer")
	public String transfer(@RequestParam int from,
			@RequestParam int to,
			@RequestParam double amount) {
		
		bankService.trasferMoney(from, to, amount);
		
		return "transfer Successful";
	}
	
}
