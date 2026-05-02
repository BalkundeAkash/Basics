package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {

	@Autowired
	private BankService bankService;

	
	//create account
	@PostMapping("/create")
	Account create(@RequestBody Account account) {
		return bankService.createAccount(account.getName(),account.getBalance());
	}
	
	//git single account
	@GetMapping("/{id}")
	public Account getAccount(@PathVariable int id) {
		return bankService.getAccount(id);
	}
	
	
	//get all accounts
	@GetMapping("/all")
	public List<Account> getAll(){
		return bankService.getAllAccounts();
	}
	
	
	//transfer money
	@PostMapping("/transfer")
	public String transder(@RequestParam int from ,
							@RequestParam int to,
							@RequestParam double amount) {
		bankService.transferMoney(from, to, amount);
	
		return "Tranfer successfully";
	}
	
}


//Testing
//POST /bank/create
//
/* 
 
// 	{
//  		"name": "Akash",
//  		"balance": 5000
//	}
//	
	[
  {
    "balance": 10000,
    "id": 3,
    "name": "Savara_Jyotsna"
  },
  {
    "balance": 11000,
    "id": 4,
    "name": "Akash_Jyotsna_Balkunde"
  },
  {
    "balance": 2000,
    "id": 1,
    "name": "Akash"
  },
  {
    "balance": 42000,
    "id": 2,
    "name": "Jyotsna"
  }
]
 
 GET /bank/all
 
 POST /bank/transfer?from=1&to=2&amount=1000
 

 */
 


