package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.interfaces.AccountService;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repo;

	@Override
	public void depositMoney(Long id, int balance) {
		repo.save(new Account(id, balance));
	}

	@Override
	public List<Account> getAllAccBal() {
		return repo.findAll();
	}

}