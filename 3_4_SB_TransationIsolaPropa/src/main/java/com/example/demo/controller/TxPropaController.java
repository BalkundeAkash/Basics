package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.interfaces.TxPropaService;

@RestController
public class TxPropaController {

	@Autowired
	TxPropaService txPropaService;

	@GetMapping("/required")
	public String required() {
		try {
			txPropaService.required();
		} catch (Exception e) {
			return "Transaction Rolled Back";
		}
		return "Success";
	}

	@GetMapping("/requires-new")
	public String requiresNew() {
		try {
			txPropaService.requiresNew();
		} catch (Exception e) {
			return "Outer rolled back, inner committed";
		}
		return "Success";
	}

	@GetMapping("/supports")
	public String supports() {
		try {
			txPropaService.supports();
		} catch (Exception e) {
			return "Transaction Rolled Back";
		}
		return "Success";
	}

	@GetMapping("/not-supported")
	public String notSupported() {
		try {
			txPropaService.notSupported();
		} catch (Exception e) {
			return "Outer rolled back, inner NOT_SUPPORTED committed";
		}
		return "Success";
	}

	@GetMapping("/never")
	public String never() {
		try {
			txPropaService.never();
		} catch (Exception e) {
			return "Exception: NEVER cannot run inside TX";
		}
		return "Success";
	}

	@GetMapping("/mandatory")
	public String mandatory() {
		try {
			txPropaService.mandatory();
		} catch (Exception e) {
			return "Transaction Rolled Back";
		}
		return "Success";
	}

}
