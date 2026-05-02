package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;
import com.example.demo.interfaces.TxPropaService;
import com.example.demo.repository.AccountRepository;
import com.example.demo.supporttx.SupportingMethods;

@Service
public class TxPropaServiceImpl implements TxPropaService {

	@Autowired
	AccountRepository repo;

	@Autowired
	SupportingMethods supportingMethods;

	// REQUIRED
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void required() {

		Account a = repo.findById(1L).get();
		Account b = repo.findById(2L).get();

		a.setBalance(a.getBalance() - 100);
		b.setBalance(b.getBalance() + 100);

		repo.save(a);
		repo.save(b);

		System.out.println("Transfer done");

		throw new RuntimeException("Force rollback");
	}

	/*
	 REQUIRED Propagation
	 --------------------
	 Theory:
	 If there is existing transaction then join that transaction.
	 If there is no transaction then create new transaction.

	 Flow:
	 Outer transaction started.
	 Account 1 -> Debit 100
	 Account 2 -> Credit 100
	 Exception occurs.

	 Output:
	 Transfer done
	 Exception : Force rollback

	 Database Result:
	 Account 1 = 1000
	 Account 2 = 1000

	 Conclusion:
	 Both operations are rolled back because both are part of same transaction.
	*/
	
//-------------------------------------------------------------------------------	

	// REQUIRES_NEW
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void requiresNew() {

		Account a = repo.findById(3L).get();
		a.setBalance(a.getBalance() - 100);
		repo.save(a);

		// separate transaction
		supportingMethods.credit(4L, 100);

		throw new RuntimeException("Outer TX rollback");
	}

	/*
	 REQUIRES_NEW Propagation
	 ------------------------
	 Theory:
	 If there is existing transaction then pause that transaction,
	 create new transaction, complete it, then resume old transaction.

	 Flow:
	 Outer transaction started.
	 Account 3 -> Debit 100

	 Existing transaction paused.

	 New transaction started.
	 Account 4 -> Credit 100
	 New transaction committed.

	 Outer transaction resumed.
	 Exception occurs.

	 Output:
	 Inner TX committed (REQUIRES_NEW)
	 Exception : Outer TX rollback

	 Database Result:
	 Account 3 = 1000   (Rolled Back)
	 Account 4 = 1100   (Committed)

	 Conclusion:
	 Outer transaction rolled back,
	 but inner transaction committed successfully.
	*/
	
//------------------------------------------------------------------------------	

	// Support
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void supports() {

		System.out.println("Outer TX started");

		// call SUPPORTS method
		supportingMethods.supportsMethod(5L, 6L);

		throw new RuntimeException("Force rollback");
	}

	/*
	 SUPPORTS Propagation
	 --------------------
	 Theory:
	 If transaction exists then join transaction.
	 If transaction does not exist then execute without transaction.

	 Flow:
	 Outer transaction started.
	 SUPPORTS method joined existing transaction.

	 Account 5 -> Debit 100
	 Account 6 -> Credit 100

	 Exception occurs.

	 Output:
	 Outer TX started
	 SUPPORTS method executed
	 Exception : Force rollback

	 Database Result:
	 Account 5 = 1000
	 Account 6 = 1000

	 Conclusion:
	 SUPPORTS joined outer transaction,
	 so both operations rolled back.
	*/
	
//------------------------------------------------------------------------------

	// NotSupported
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void notSupported() {

		System.out.println("Outer TX started");

		supportingMethods.notSupportedMethod(7L, 8L);

		throw new RuntimeException("Force rollback");
	}

	/*
	 NOT_SUPPORTED Propagation
	 -------------------------
	 Theory:
	 If transaction exists then pause that transaction
	 and execute without transaction.

	 Flow:
	 Outer transaction started.

	 Existing transaction paused.

	 NOT_SUPPORTED method executed without transaction.

	 Account 7 -> Debit 100
	 Account 8 -> Credit 100

	 Method completed.

	 Outer transaction resumed.
	 Exception occurs.

	 Output:
	 Outer TX started
	 NOT_SUPPORTED executed (no TX)
	 Exception : Force rollback

	 Database Result:
	 Account 7 = 900
	 Account 8 = 1100

	 Conclusion:
	 Inner method executed without transaction,
	 so data committed immediately and rollback did not affect it.
	*/
	
//------------------------------------------------------------------------------

	// Never
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void never() {

		System.out.println("Outer TX started");

		// this will throw exception
		supportingMethods.neverMethod(9L, 10L);
	}

	/*
	 NEVER Propagation
	 -----------------
	 Theory:
	 If transaction exists then directly throw exception.

	 Flow:
	 Outer transaction started.

	 NEVER method called.

	 Transaction already exists,
	 so exception thrown immediately.

	 Output:
	 Outer TX started
	 Exception : Existing transaction found for transaction marked with propagation 'never'

	 Database Result:
	 Account 9 = 1000
	 Account 10 = 1000

	 Conclusion:
	 NEVER does not allow existing transaction.
	 Method execution stopped immediately.
	*/
	
//-----------------------------------------------------------------------------

	// Mandatory
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void mandatory() {

		System.out.println("Outer TX started");

		supportingMethods.mandatoryMethod(11L, 12L);

//		throw new RuntimeException("Force rollback");
	}
	
	/*
	 MANDATORY Propagation
	 ---------------------
	 Theory:
	 Transaction is compulsory.
	 If transaction does not exist then exception occurs.

	 Flow:
	 Outer transaction started.

	 MANDATORY method joined existing transaction.

	 Account 11 -> Debit 100
	 Account 12 -> Credit 100

	 Exception occurs.

	 Output:
	 Outer TX started
	 MANDATORY executed
	 Exception : Force rollback

	 Database Result:
	 Account 11 = 1000
	 Account 12 = 1000

	 Conclusion:
	 MANDATORY joined outer transaction,
	 so all operations rolled back.
	*/
	
}
