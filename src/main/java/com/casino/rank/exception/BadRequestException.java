package com.casino.rank.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(){
        super();
    }

    public BadRequestException(String message){
        super(message);
    }

}
