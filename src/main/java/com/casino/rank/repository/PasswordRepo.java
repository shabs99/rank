package com.casino.rank.repository;

import com.casino.rank.model.Password;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepo extends CrudRepository<Password, String> {
}
