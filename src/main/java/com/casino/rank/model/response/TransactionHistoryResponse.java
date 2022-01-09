package com.casino.rank.model.response;

import com.casino.rank.model.Transaction;
import lombok.Data;
import java.util.List;

@Data
public class TransactionHistoryResponse {
    private String playerId;
    private List<Transaction> transactionList;
    private String message;
}
