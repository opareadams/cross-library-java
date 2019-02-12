/**
 * 
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.model.Transaction;
import com.crossover.techtrial.repositories.BookRepository;
import com.crossover.techtrial.repositories.MemberRepository;
import com.crossover.techtrial.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author crossover
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService{


  @Autowired TransactionRepository transactionRepository;

  
  public Transaction save(Transaction transaction) {

    return transactionRepository.save(transaction);
  }
  
  public Transaction findById(Long transactionId) {
    Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
    if (optionalTransaction.isPresent()) {
      return optionalTransaction.get();
    }else return null;
  }
  
  public List<Transaction> findAll() {
    return transactionRepository.findAll();
  }

  public Transaction findByBookId(Long bookId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findByBookId(bookId);
        if (optionalTransaction.isPresent()) {
            return optionalTransaction.get();
        }else return null;
  }

  public List<Transaction> findAllBorrrowedBooks(Long memberId){
      return transactionRepository.findAllBorrrowedBooks(memberId);
  }


}
