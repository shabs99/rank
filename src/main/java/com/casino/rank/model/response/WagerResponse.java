package com.casino.rank.model.response;

import lombok.Data;

@Data
public class WagerResponse {
    private String transactionId;
    private String playerId;
    private String message;
}
