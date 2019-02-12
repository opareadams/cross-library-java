/**
 * 
 */
package com.crossover.techtrial.controller;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import com.crossover.techtrial.model.Book;
import com.crossover.techtrial.service.BookService;
import com.crossover.techtrial.service.MemberService;
import com.crossover.techtrial.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.crossover.techtrial.model.Transaction;
import com.crossover.techtrial.repositories.BookRepository;
import com.crossover.techtrial.repositories.MemberRepository;
import com.crossover.techtrial.repositories.TransactionRepository;

/**
 * @author kshah
 *
 */
@RestController
public class TransactionController {

  @Autowired
  BookService bookService;

  @Autowired
  MemberService memberService;

  @Autowired
  TransactionService  transactionService;
  /*
   * PLEASE DO NOT CHANGE SIGNATURE OR METHOD TYPE OF END POINTS
   * Example Post Request :  { "bookId":1,"memberId":33 }
   */
  @PostMapping(path = "/api/transaction")
  public ResponseEntity<Transaction> issueBookToMember(@RequestBody Map<String, Long> params){
    
    Long bookId = params.get("bookId");
    Long memberId = params.get("memberId");
    Transaction transaction = new Transaction();
    transaction.setBook(bookService.findById(bookId));
    transaction.setMember(memberService.findById(memberId));
    transaction.setDateOfIssue(LocalDateTime.now());

    //prevent issuing of same book twice without prior submission
    Transaction transaction1 = transactionService.findByBookId(bookId);
    if (transaction1 != null) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    //check if book exists or not in database
    Book book = bookService.findById(bookId);
    if (book==null){

      return ResponseEntity.notFound().build();
    }

    //prevent issuing more than 5 books
    List<Transaction> transaction2 = transactionService.findAllBorrrowedBooks(memberId);
    if (transaction2.size() >= 5) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    return ResponseEntity.ok().body(transactionService.save(transaction));
  }
  /*
   * PLEASE DO NOT CHANGE SIGNATURE OR METHOD TYPE OF END POINTS
   */
  @PatchMapping(path= "/api/transaction/{transaction-id}/return")
  public ResponseEntity<Transaction> returnBookTransaction(@PathVariable(name="transaction-id") Long transactionId){
    Transaction transaction = transactionService.findById(transactionId);

    if(transaction.getDateOfReturn() != null){
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    transaction.setDateOfReturn(LocalDateTime.now());



    return ResponseEntity.ok().body(transactionService.save(transaction));
  }

}
