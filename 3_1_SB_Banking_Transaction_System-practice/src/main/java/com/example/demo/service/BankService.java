package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Application;
import com.example.demo.entity.Account;
import com.example.demo.repository.BankRepository;

@Service
public class BankService {

	@Autowired
	private final Application application;

	@Autowired
	private BankRepository bankRepository;

	BankService(Application application) {
		this.application = application;
	}

	// create new account
	public Account createAccount(String name, double balance) {

		Account account = new Account();

		account.setName(name);
		account.setBalance(balance);

		return bankRepository.save(account); // save to DB
	}

	// ✅ Get account by ID
	public Account getAccount(int id) {
		return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
	}

	// Get all accounts
	public java.util.List<Account> getAllAccounts() {

		return bankRepository.findAll();

	}

	// Main transcational method

	public void transferMoney(int fromId, int toId, double amount) {

		// Step 1 : fetch account
		Account from = getAccount(fromId);
		Account to = getAccount(toId);

		// step 2 : Validations

		if (from.getBalance() < amount) {
			throw new RuntimeException("Insufficenet Balace");

		}

		// Step 3 : Debit
		from.setBalance((from.getBalance() - amount));

		// Step 4: Credit
		to.setBalance(to.getBalance() + amount);

		// step 5 save (optional due to dirty checking)
		bankRepository.save(from);
		bankRepository.save(to);

	}

}
