package com.example.demo.interfaces;

public interface TxIsoService {

	void readUncommittedExample();

	void readCommittedExample();

	void repeatableReadExample();

	void serializableExample();

}