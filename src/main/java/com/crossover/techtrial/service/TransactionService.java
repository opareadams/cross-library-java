/**
 * 
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.model.Transaction;

import java.util.List;

/**
 * RideService for rides.
 * @author crossover
 *
 */
public interface TransactionService {
  
  public Transaction save(Transaction transaction);
  
  public Transaction findById(Long transactionId);
  
  public List<Transaction> findAll();

  public Transaction findByBookId(Long bookId);

  public List<Transaction> findAllBorrrowedBooks(Long memberId);
  
}
