package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private WalletService service;

	@PostMapping("/send")
	public String send(@RequestParam int from, @RequestParam int to, @RequestParam double amount,
			@RequestParam String txId) {
		return service.sendMoney(from, to, amount, txId);
	}

}
