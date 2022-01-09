package com.casino.rank.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Data
public class Transaction {

    @Id
    @Column
    private String transactionId;
    @Column
    private String playerId;
    @Column
    private double wagerAmount;
    @Column
    private double winAmount;
}
