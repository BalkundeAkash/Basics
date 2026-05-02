package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Account;
import com.example.demo.interfaces.AccountService;

import java.util.List;

@RestController
@RequestMapping("/tx")
public class TxController {

	@Autowired
	private AccountService service;

	@PostMapping("/create")
	public String create(@RequestParam Long id, @RequestParam int balance) {
		service.depositMoney(id, balance);
		return "Created";
	}

	@GetMapping("/all")
	public List<Account> all() {
		return service.getAllAccBal();
	}

}