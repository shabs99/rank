package com.casino.rank.model.response;
import lombok.Data;

@Data
public class DepositResponse {
    private String playerId;
    private double balance;
    private String message;
}
