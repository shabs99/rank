package com.casino.rank.model.request;
import lombok.Data;

@Data
public class TransactionHistoryRequest {
    private String password;
    private String playerId;
}
