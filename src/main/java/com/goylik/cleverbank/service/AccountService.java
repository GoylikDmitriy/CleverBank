package com.goylik.cleverbank.service;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Bank;
import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    String openAccount(Bank bank, User user, BigDecimal initialFee) throws ServiceException;
    Account getById(Long accountId) throws ServiceException;
    Account getByNumber(String accountNumber) throws ServiceException;
    List<Account> getAll() throws ServiceException;
    void addToBalance(String accountNumber, BigDecimal amount) throws ServiceException;
    void subtractFromBalance(String accountNumber, BigDecimal amount) throws ServiceException;
    void deleteAll() throws ServiceException;
}