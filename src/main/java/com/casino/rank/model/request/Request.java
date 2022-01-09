package com.casino.rank.model.request;

import lombok.Data;

@Data
public class Request {
    private String playerId;
    private String transactionId;
    private double amount;
}
