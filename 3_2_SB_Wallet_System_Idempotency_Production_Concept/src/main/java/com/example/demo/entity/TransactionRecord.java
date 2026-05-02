package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TransactionRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String transcationId; // unique idempotency key

	private int fromWallet;
	private int toWallet;
	private double amount;

	private String status; // success/failed
}
