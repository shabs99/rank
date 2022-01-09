package com.casino.rank.repository;


import com.casino.rank.model.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProfileRepo extends CrudRepository<Profile, String> {

    @Transactional
    @Modifying
    @Query("update Profile p set p.balance = :balance where p.playerId = :id")
    void updatePlayerProfileBalance(String id, double balance);
}
