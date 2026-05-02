package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRespository;

import jakarta.transaction.Transactional;

@Service
public class BankService {

	private AccountRespository repo;

	@Transactional
	public void trasferMoney(int fromID, int toId, double amount) {

		Account from = repo.findById(fromID).orElseThrow(() -> new RuntimeException("Sender not found"));

		Account to = repo.findById(toId).orElseThrow(() -> new RuntimeException("Receiever Not found"));

		if (from.getBalanace() < amount) {
			throw new RuntimeException("Insufficient Balance");
		}

		from.setBalanace(from.getBalanace() - amount);
		repo.save(from);

		to.setBalanace(to.getBalanace() + amount);
		repo.save(to);
	}
}
