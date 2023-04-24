package com.example.carrentalbackend.repository;

import com.example.carrentalbackend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account,Long> {
    Account findAccountByUsername(String username);
}
