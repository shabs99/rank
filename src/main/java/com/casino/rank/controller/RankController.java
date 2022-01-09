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
    public ResponseEntity<BalanceResponse> getBalance(@RequestParam String playerId){
        BalanceResponse balanceResponse = casinoService.getBalance(playerId);
        if(null != balanceResponse){
            return new ResponseEntity<>(balanceResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/wage")
    public ResponseEntity<WagerResponse> wage(@RequestBody Request request) {
            WagerResponse wagerResponse = casinoService.wageBet(request);
            if (null != wagerResponse) {
                if (null == wagerResponse.getPlayerId()) {
                    return new ResponseEntity<>(wagerResponse, HttpStatus.I_AM_A_TEAPOT);
                }
                return new ResponseEntity<>(wagerResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> depost(@RequestBody Request request){
        DepositResponse depositResponse = casinoService.depositWinnings(request);
        if(null != depositResponse){
            return  new ResponseEntity<>(depositResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/getTransactions")
    public ResponseEntity<TransactionHistoryResponse> getTransactions(@RequestBody TransactionHistoryRequest request) {
        TransactionHistoryResponse response = casinoService.getTransactionHistory(request);
        if (null == response) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
