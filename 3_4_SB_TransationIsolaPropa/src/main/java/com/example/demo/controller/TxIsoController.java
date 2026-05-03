package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.interfaces.TxIsoService;

@RestController
public class TxIsoController {

	@Autowired
	TxIsoService txIsoService;

	@GetMapping("/read-uncommitted")
	public String ru() {

		txIsoService.readUncommittedExample();

		return "Check Console";
	}

	@GetMapping("/read-committed")
	public String rc() {

		txIsoService.readCommittedExample();

		return "Check Console";
	}

	@GetMapping("/repeatable-read")
	public String rr() {

		txIsoService.repeatableReadExample();

		return "Check Console";
	}

	@GetMapping("/serializable")
	public String sr() {

		txIsoService.serializableExample();

		return "Check Console";
	}
}