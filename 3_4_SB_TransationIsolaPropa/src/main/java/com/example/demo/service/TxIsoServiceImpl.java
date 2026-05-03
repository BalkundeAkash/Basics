package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.TxIsoService;
import com.example.demo.supporttx.IsolationSupportMethods;

@Service
public class TxIsoServiceImpl implements TxIsoService {

	@Autowired
	IsolationSupportMethods iso;

	// READ_UNCOMMITTED
	@Override
	public void readUncommittedExample() {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				iso.writerRU(14L);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				iso.readerRU(14L);
			}
		});

		t1.start();

		sleep(2000);

		t2.start();
	}
	
	/* t1 will start then it will execute logic on 13L so 13L will 
	  become 900 but tx not commited yet it will sleep for 10 sec because 
	  of sleep(10000) methods of suppotive method but after 2 sec 
	  t2 will start and call iso.readerRU(13L) it will read the data 
	  (it will see 900 if we are using MySQL) but we are using 
	  postgreSql so it silently converts: READ_UNCOMMITTED to 
	  READ_COMMITTED so we will see 1000 not 900 but after tx 
	  completion DB record will be like 900 because tx is commited now  
	*/	
	
	/*
	READ_UNCOMMITTED
	----------------
	Application can read uncommitted data.

	This may cause Dirty Read.

	Dirty Read:
	Reading data which is updated but not committed yet.

	Note:
	In PostgreSQL READ_UNCOMMITTED behaves like READ_COMMITTED.
	So dirty read cannot actually happen.
	*/
	
//---------------------------------------------------------------------------
	
	// READ_COMMITTED
	@Override
	public void readCommittedExample() {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				iso.writerRC(15L);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				iso.readerRC(15L);
			}
		});

		t1.start();

		sleep(2000);

		t2.start();
	}

	/* t1 will start it execute the logic on 15L then it will become
	900 but then it will be in sleep for 10 sec and yet tx is not 
	committed so now after 2 sec t2 will start and then it will reads 
	the committed data even though 15L is 900 but its not committed so 
	it will reads 1000 because 1000 is committed but in Db after 10 sec tx 
    will finished and value is committed so Db record for 15L will show 900
    */
	
	/*
	READ_COMMITTED
	--------------
	Application reads only committed data.

	Uncommitted data is not visible.

	Dirty Read is prevented.
	*/

//---------------------------------------------------------------------------

	// REPEATABLE_READ
	@Override
	public void repeatableReadExample() {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				iso.readerRR(17L);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				iso.writerRR(17L);
			}
		});

		t1.start();
		t2.start();
	}

	/*
	 T1 and T2 start at same time.
	 T2 waits for 2 sec.
	 T1 reads account 17 and gets 1000.
	 At that moment PostgreSQL creates a snapshot for T1.
	 After 2 sec T2 updates account 17 from 1000 to 900 and commits.
	 Actual database value becomes 900.
	 But when T1 reads again, it does not read latest database value.
	 It reads its old snapshot, so it still gets 1000. 
	 */
	
	/*
	REPEATABLE_READ
	---------------
	If same transaction reads same row multiple times,
	it always gets same result.

	Non-repeatable read is prevented.

	Even if another transaction commits new value,
	current transaction keeps seeing old snapshot.
	*/

//---------------------------------------------------------------------------
	
	// SERIALIZABLE
	@Override
	public void serializableExample() {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				iso.readerSR(19L);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				iso.writerSR(19L);
			}
		});

		t1.start();
		t2.start();
	}

	
	/* (Normal case)
	  T1 and T2 start at same time.
	  T2 waits for 2 sec.
	  T1 reads account 19 and gets 1000.
	  At that moment PostgreSQL creates a snapshot for T1.
	  After 2 sec T2 updates account 19 from 1000 to 900 and commits.
	  Actual database value becomes 900.
	  But when T1 reads again, it does not read latest database value.
	  It reads its old snapshot, so it still gets 1000.
	  In SERIALIZABLE PostgreSQL also checks whether
	  all transactions can behave as if they ran one by one.
	  In this case no conflict happens,
	  so both transactions complete successfully.
	*/ 

	/* (Kamatun geleli case)
	  T1 and T2 start at same time.
	  T1 reads account 19 and gets 1000.
	  T2 also reads account 19 and gets 1000.
	  Both transactions create their own snapshot.
	  T1 updates account 19 from 1000 to 900 and commits.
	  Actual database value becomes 900.
	  Now T2 also tries to update using old value 1000.
	  PostgreSQL checks whether both transactions
	  can behave as if they ran one by one.
	  PostgreSQL detects conflict.
	  To keep data correct, PostgreSQL aborts one transaction.(mostly 2nd)
	  Exception:
	  could not serialize access due to concurrent update
	*/
	
	/*
	SERIALIZABLE
	------------
	Theory:
	Highest isolation level.

	Transactions behave as if executed one by one.

	PostgreSQL uses snapshot + conflict detection.

	Flow:
	T1 reads account 19 = 1000.
	Snapshot created.

	T2 updates account 19 = 900 and commits.

	T1 reads again.

	Output:
	First Read = 1000
	Writer SR committed
	Second Read = 1000

	Database Result:
	Account 19 = 900

	Conclusion:
	T1 reads old snapshot.
	If conflict occurs, PostgreSQL may abort one transaction.
	*/
	
//---------------------------------------------------------------------------

	
	public void sleep(long time) {

		try {
			Thread.sleep(time);
		} catch (Exception e) {

		}
	}
}