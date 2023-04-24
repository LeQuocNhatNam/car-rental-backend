package com.example.carrentalbackend.service.impl;

import com.example.carrentalbackend.model.Account;
import com.example.carrentalbackend.repository.IAccountRepository;
import com.example.carrentalbackend.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;
    @Override
    public Account findAccountByUsername(String username) {
        return this.iAccountRepository.findAccountByUsername(username);
    }
}
