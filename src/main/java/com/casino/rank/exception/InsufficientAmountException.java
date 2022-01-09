package com.casino.rank.exception;

public class InsufficientAmountException extends RuntimeException{

    public InsufficientAmountException(){
        super();
    }

    public InsufficientAmountException(String message){
        super(message);
    }

}
