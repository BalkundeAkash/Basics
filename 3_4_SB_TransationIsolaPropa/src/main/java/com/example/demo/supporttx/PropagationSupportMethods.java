package com.example.demo.supporttx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PropagationSupportMethods {

	@Autowired
	AccountRepository repo;

	// REQUIRES_NEW
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void credit(Long id, int amount) {

		Account acc = repo.findById(id).get();
		acc.setBalance(acc.getBalance() - amount);

		repo.save(acc);

		System.out.println("Inner TX committed (REQUIRES_NEW)");
	}

	// SUPPORTED
	@Transactional(propagation = Propagation.SUPPORTS)
	public void supportsMethod(Long from, Long to) {

		Account a = repo.findById(from).get();
		Account b = repo.findById(to).get();

		// A → debit
		a.setBalance(a.getBalance() - 100);

		// B → credit
		b.setBalance(b.getBalance() + 100);

		repo.save(a);
		repo.save(b);

		System.out.println("SUPPORTS method executed");
	}

	// NOT_SUPPORTED → always runs WITHOUT TX
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void notSupportedMethod(Long from, Long to) {

		Account a = repo.findById(from).get();
		Account b = repo.findById(to).get();

		a.setBalance(a.getBalance() - 100);
		b.setBalance(b.getBalance() + 100);

		repo.save(a);
		repo.save(b);

		System.out.println("NOT_SUPPORTED executed (no TX)");
	}

	// NEVER → must NOT have TX
	@Transactional(propagation = Propagation.NEVER)
	public void neverMethod(Long from, Long to) {

		Account a = repo.findById(from).get();
		Account b = repo.findById(to).get();

		a.setBalance(a.getBalance() - 100);
		b.setBalance(b.getBalance() + 100);

		repo.save(a);
		repo.save(b);

		System.out.println("NEVER executed");
	}

	// MANDATORY → must HAVE TX
	@Transactional(propagation = Propagation.MANDATORY)
	public void mandatoryMethod(Long from, Long to) {

		Account a = repo.findById(from).get();
		Account b = repo.findById(to).get();

		a.setBalance(a.getBalance() - 100);
		b.setBalance(b.getBalance() + 100);

		repo.save(a);
		repo.save(b);

		System.out.println("MANDATORY executed");
	}

}
