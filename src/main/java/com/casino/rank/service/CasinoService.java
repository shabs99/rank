package com.casino.rank.service;

import com.casino.rank.model.request.Request;
import com.casino.rank.model.request.TransactionHistoryRequest;
import com.casino.rank.model.response.BalanceResponse;
import com.casino.rank.model.response.DepositResponse;
import com.casino.rank.model.response.TransactionHistoryResponse;
import com.casino.rank.model.response.WagerResponse;


public interface CasinoService {

    WagerResponse wagerBet(Request request);
    DepositResponse depositWinnings(Request request);
    BalanceResponse getBalance(String playerId);
    TransactionHistoryResponse getTransactionHistory(TransactionHistoryRequest request);
}
