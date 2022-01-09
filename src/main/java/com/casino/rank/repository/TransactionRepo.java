package com.casino.rank.repository;

import com.casino.rank.model.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TransactionRepo extends CrudRepository<Transaction, String> {

    @Transactional
    @Modifying
    @Query("update Transaction t set t.winAmount = :winnings where t.transactionId = :id")
    void updateTransactionWinnings(String id, Double winnings);

    List<Transaction> getAllByPlayerId(String playerId);
}
