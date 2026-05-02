package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TransactionRecord;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionRecord, Integer> {
	
	Optional<TransactionRecord> findByTranscationId(String transcationID);

}
