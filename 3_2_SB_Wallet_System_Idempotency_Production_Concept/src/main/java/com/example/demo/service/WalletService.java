package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TransactionRecord;
import com.example.demo.entity.Wallet;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.WalletRepository;

import jakarta.transaction.Transactional;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Transactional
	public String sendMoney(int fromId, int toId, double amount, String txId) {

		// Step 1 : Check idempotency
		Optional<TransactionRecord> existing = transactionRepository.findByTranscationId(txId);

		if (existing.isPresent()) {
			return "Duplicate Transcation - Already processed";
		}

		// Step 2 : fetch Wallets
		Wallet from = walletRepository.findById(fromId).orElseThrow(() -> new RuntimeException("Sender Not Found"));

		Wallet to = walletRepository.findById(toId).orElseThrow(() -> new RuntimeException("Receiver Not found"));

		// setp 3 Validate balance
		if (from.getBalance() < amount) {
			throw new RuntimeException("Insufficent Balance");
		}

		// step 4 Debit & credit
		from.setBalance(from.getBalance() - amount);
		to.setBalance(to.getBalance() + amount);

		// Step 5 save transcation

		TransactionRecord tx = new TransactionRecord();
		tx.setTranscationId(txId);
		tx.setFromWallet(fromId);
		tx.setToWallet(toId);
		tx.setAmount(amount);
		tx.setStatus("SUCCESS");

		transactionRepository.save(tx);

		return "Tranfer Successful";

	}
}
