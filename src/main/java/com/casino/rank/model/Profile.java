package com.casino.rank.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Table
@Entity
@Data
public class Profile {

    @Id
    @Column
    private String playerId;

    @Column
    private double balance;
    @Column
    private int promoCount;
}
