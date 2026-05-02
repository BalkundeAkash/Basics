package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.entity.Account;

public interface AccountService {

	void depositMoney(Long id, int balance);

	List<Account> getAllAccBal();

}