/**
 * 
 */
package com.crossover.techtrial.repositories;

import com.crossover.techtrial.model.Member;
import com.crossover.techtrial.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

/**
 * @author crossover
 *
 */
@RestResource(exported = false)
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAll();

    @Query(value = "SELECT * FROM transaction WHERE book_id = :bookId AND date_of_return is null",nativeQuery = true)
    Optional<Transaction> findByBookId(@Param("bookId") Long bookId);

    @Query(value = "SELECT * FROM transaction WHERE member_id = :memberId AND date_of_return is null",nativeQuery = true)
    List<Transaction> findAllBorrrowedBooks(@Param("memberId") Long memberId);

}
