package com.example.carrentalbackend.service;

import com.example.carrentalbackend.model.Account;

public interface IAccountService {
    Account findAccountByUsername(String username);
}
