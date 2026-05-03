package com.example.demo.supporttx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

@Component
public class IsolationSupportMethods {

	@Autowired
	AccountRepository repo;

	// READ_UNCOMMITTED

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void writerRU(Long id) {

		Account acc = repo.findById(id).get();

		acc.setBalance(acc.getBalance() - 100);

		repo.save(acc);

		System.out.println("Writer RU updated but not committed");

		sleep(10000);

		System.out.println("Writer RU committed");
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void readerRU(Long id) {

		Account acc = repo.findById(id).get();

		System.out.println("Reader RU sees = " + acc.getBalance());
	}

//---------------------------------------------------------------------------

	// READ_COMMITTED

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void writerRC(Long id) {

		Account acc = repo.findById(id).get();

		acc.setBalance(acc.getBalance() - 100);

		repo.save(acc);

		System.out.println("Writer RC updated but not committed");

		sleep(10000);

		System.out.println("Writer RC committed");
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void readerRC(Long id) {

		Account acc = repo.findById(id).get();

		System.out.println("Reader RC sees = " + acc.getBalance());
	}

//---------------------------------------------------------------------------

	// REPEATABLE_READ

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void readerRR(Long id) {

		Account acc1 = repo.findById(id).get();

		System.out.println("First Read = " + acc1.getBalance());

		sleep(10000);

		Account acc2 = repo.findById(id).get();

		System.out.println("Second Read = " + acc2.getBalance());
	}

	@Transactional
	public void writerRR(Long id) {

		sleep(2000);

		Account acc = repo.findById(id).get();

		acc.setBalance(acc.getBalance() - 100);

		repo.save(acc);

		System.out.println("Writer RR committed");
	}

//---------------------------------------------------------------------------

	// SERIALIZABLE

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void readerSR(Long id) {

		Account acc1 = repo.findById(id).get();

		System.out.println("First Read = " + acc1.getBalance());

		sleep(10000);

		Account acc2 = repo.findById(id).get();

		System.out.println("Second Read = " + acc2.getBalance());
	}

	@Transactional
	public void writerSR(Long id) {

		sleep(2000);

		Account acc = repo.findById(id).get();

		acc.setBalance(acc.getBalance() - 100);

		repo.save(acc);

		System.out.println("Writer SR committed");
	}	

//---------------------------------------------------------------------------

	// helper
	private void sleep(long time) {

		try {
			Thread.sleep(time);
		} catch (Exception e) {

		}
	}
}