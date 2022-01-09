package com.casino.rank.service;

import com.casino.rank.exception.BadRequestException;
import com.casino.rank.exception.InsufficientAmountException;
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
import com.casino.rank.util.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

        BalanceResponse response = new BalanceResponse();
        log.info("getting balance");
        Optional<Profile> profile = profileRepo.findById(playerId);
        if(profile.isPresent()) {

            response.setBalance(profile.get().getBalance());
            response.setPlayerId(profile.get().getPlayerId());
            response.setMessage("Successful");
            log.info("balance retrieved successfully");
            return response;
        }
        log.info("failed to get balance");
        throw new BadRequestException("Invalid PlayerId : "+ playerId);
    }


    public DepositResponse depositWinnings(Request request) {
        log.info("depositing winnings");
        DepositResponse response = new DepositResponse();
        if(!Validator.isValidAmount(request.getAmount())){
            log.info("Invalid Amount");
            throw new BadRequestException("Invalid Amount : Amount should be greater than 0" );
        }
        Optional<Profile> profile = profileRepo.findById(request.getPlayerId());
        if (profile.isPresent()) {
            Optional<Transaction> transaction = transactionRepo.findById(request.getTransactionId());
            if (transaction.isPresent()) {
                double balance = profile.get().getBalance() + request.getAmount();
                profileRepo.updatePlayerProfileBalance(request.getPlayerId(), balance);
                transactionRepo.updateTransactionWinnings(request.getTransactionId(), request.getAmount());

                response.setBalance(balance);
                response.setMessage("Successful");
                response.setPlayerId(request.getPlayerId());
                log.info("Winnings deposited successfully");
                return response;
            }
            log.info("failed to deposit Winnings: Invalid TransactionId");
            throw  new BadRequestException("Invalid PlayerId : "+request.getPlayerId());
        }
        log.info("failed to deposit Winnings: Invalid PlayerId");
        throw  new BadRequestException("Invalid PlayerId : "+request.getPlayerId());
    }

    public WagerResponse wagerBet(Request request) {
        log.info("Wagering");
        if(!Validator.isValidAmount(request.getAmount())){
            log.info("Invalid Amount");
            throw new BadRequestException("Invalid Amount : Amount should be greater than 0");
        }

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
            if (profile.get().getBalance() >= request.getAmount()) {
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
                log.info("Failed to make Transaction: Insufficient funds");
                throw new InsufficientAmountException("Failed to make Transaction: Insufficient funds");
            }
        }
        log.info("Failed to make Transaction:  Invalid UserId");
        throw  new BadRequestException("Invalid PlayerId : "+request.getPlayerId());
    }

    public TransactionHistoryResponse getTransactionHistory(TransactionHistoryRequest request) {

        TransactionHistoryResponse response = new TransactionHistoryResponse();
        response.setPlayerId(request.getPlayerId());
        if (passwordRepo.existsById(request.getPassword())) {
            List<Transaction> transactionList = transactionRepo.getAllByPlayerId(request.getPlayerId());
            Collections.reverse(transactionList);
            List<Transaction> subTransactions;
            if(transactionList.isEmpty()){
                log.info("No Transactions");
                response.setMessage("No Transactions");
                return response;
            }
            int size  = transactionList.size();
            if(size>10){
                subTransactions = transactionList.subList(0, 10);
                response.setTransactionList(subTransactions);
                log.info("Transactions Retrieved Successfully");
                response.setMessage("Successful");
                return response;
            }
                subTransactions = transactionList;
                response.setTransactionList(subTransactions);
                response.setMessage("Successful");
                log.info("Transactions Retrieved Successfuly");
                return response;
        }
        log.info("Failed to retrieve Transactions: Invalid Password");
        throw  new BadRequestException("Invalid Password");
    }
}
