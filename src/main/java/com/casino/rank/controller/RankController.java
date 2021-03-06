package com.casino.rank.controller;

import com.casino.rank.model.request.Request;
import com.casino.rank.model.request.TransactionHistoryRequest;
import com.casino.rank.model.response.BalanceResponse;
import com.casino.rank.model.response.DepositResponse;
import com.casino.rank.model.response.TransactionHistoryResponse;
import com.casino.rank.model.response.WagerResponse;
import com.casino.rank.service.CasinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RankController {

    @Autowired
    private CasinoService casinoService;

    @GetMapping("/getBalance")
    public ResponseEntity<BalanceResponse> getBalance(@RequestParam String playerId) {
        BalanceResponse balanceResponse = casinoService.getBalance(playerId);
        if (null != balanceResponse) {
            return new ResponseEntity<>(balanceResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/wager")
    public ResponseEntity<WagerResponse> wager(@RequestBody Request request) {
        WagerResponse wagerResponse = casinoService.wagerBet(request);
        return new ResponseEntity<>(wagerResponse, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> depost(@RequestBody Request request) {
        DepositResponse depositResponse = casinoService.depositWinnings(request);
        return new ResponseEntity<>(depositResponse, HttpStatus.OK);
    }

    @PostMapping("/getTransactions")
    public ResponseEntity<TransactionHistoryResponse> getTransactions(@RequestBody TransactionHistoryRequest request) {
        TransactionHistoryResponse response = casinoService.getTransactionHistory(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
