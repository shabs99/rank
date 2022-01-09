package com.casino.rank.service;

import com.casino.rank.model.Profile;
import com.casino.rank.model.Transaction;
import com.casino.rank.model.request.Request;
import com.casino.rank.model.request.TransactionHistoryRequest;
import com.casino.rank.model.response.BalanceResponse;
import com.casino.rank.model.response.DepositResponse;
import com.casino.rank.model.response.TransactionHistoryResponse;
import com.casino.rank.model.response.WagerResponse;
import com.casino.rank.repository.PasswordRepo;
import com.casino.rank.repository.ProfileRepo;
import com.casino.rank.repository.TransactionRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CasinoServiceImpl implements CasinoService {

    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    PasswordRepo passwordRepo;

    public BalanceResponse getBalance(String playerId){

        log.info("getting balance");
        Optional<Profile> profile = profileRepo.findById(playerId);
        if(profile.isPresent()) {
            BalanceResponse response = new BalanceResponse();
            response.setBalance(profile.get().getBalance());
            response.setPlayerId(profile.get().getPlayerId());
            log.info("balance retrieved successfully");
            return response;
        }
        log.info("failed to get balance");
        return null;

    }


    public DepositResponse depositWinnings(Request request) {
        log.info("depositing winnings");
        Optional<Profile> profile = profileRepo.findById(request.getPlayerId());
        if (profile.isPresent()) {
            Optional<Transaction> transaction = transactionRepo.findById(request.getTransactionId());
            if (transaction.isPresent()) {
                double balance = profile.get().getBalance() + request.getAmount();
                profileRepo.updatePlayerProfileBalance(request.getPlayerId(), balance);
                transactionRepo.updateTransactionWinnings(request.getTransactionId(), request.getAmount());
                DepositResponse response = new DepositResponse();
                response.setBalance(balance);
                response.setMessage("Successful");
                response.setPlayerId(request.getPlayerId());
                log.info("Winnings deposited successfully");
                return response;
            }
            log.info("failed to deposit Winnings: Invalid TransactionId");
            return null;
        }
        log.info("failed to deposit Winnings: Invalid PlayerId");
        return null;
    }

    public WagerResponse wageBet(Request request) {
        log.info("Wagering");
        Optional<Profile> profile = profileRepo.findById(request.getPlayerId());
        if (profile.isPresent()) {
            Optional<Transaction> transaction = transactionRepo.findById(request.getTransactionId());
            if (transaction.isPresent()) {
                WagerResponse wagerResponse = new WagerResponse();
                wagerResponse.setPlayerId(request.getPlayerId());
                wagerResponse.setTransactionId(request.getTransactionId());
                wagerResponse.setMessage("Transactoin Exists");
                log.info("Duplicate TransactionId");
                return wagerResponse;
            }
            if (profile.get().getBalance() > request.getAmount()) {
                double balance = profile.get().getBalance() - request.getAmount();
                profileRepo.updatePlayerProfileBalance(request.getPlayerId(), balance);
                Transaction transaction1 = new Transaction();
                transaction1.setTransactionId(request.getTransactionId());
                transaction1.setPlayerId(request.getPlayerId());
                transaction1.setWagerAmount(request.getAmount());
                transactionRepo.save(transaction1);

                WagerResponse wagerResponse = new WagerResponse();
                wagerResponse.setPlayerId(request.getPlayerId());
                wagerResponse.setTransactionId(request.getTransactionId());
                wagerResponse.setMessage("Successful");
                log.info("Successful Transaction");
                return wagerResponse;

            } else {
                WagerResponse wagerResponse = new WagerResponse();
                wagerResponse.setMessage("OUT OF FUNDS");
                log.info("Failed to make Transaction: Insufficient funds");
                return wagerResponse;
            }
        }
        log.info("Failed to make Transaction:  Invalid UserId");
        return null;
    }

    public TransactionHistoryResponse getTransactionHistory(TransactionHistoryRequest request) {

        TransactionHistoryResponse response = new TransactionHistoryResponse();
        response.setPlayerId(request.getPlayerId());

        if (passwordRepo.existsById(request.getPassword())) {
            List<Transaction> transactionList = transactionRepo.getAllByPlayerId(request.getPlayerId());
            List<Transaction> subTransactions;
            if(transactionList.isEmpty()){
                log.info("No Transactions");
                return response;
            }
            int size  = transactionList.size();
            if(size>10){
                subTransactions = transactionList.subList(0, 10);
                response.setTransactionList(subTransactions);
                log.info("Transactions Retrieved Successfully");
                return response;
            }
                subTransactions = transactionList;
                response.setTransactionList(subTransactions);
                log.info("Transactions Retrieved Successfuly");
                return response;
        }
        log.info("Failed to retrieve Transactions: Invalid Password");
        return null;
    }
}
