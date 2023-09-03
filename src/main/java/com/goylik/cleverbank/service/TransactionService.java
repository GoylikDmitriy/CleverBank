package com.goylik.cleverbank.service;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Transaction;
import com.goylik.cleverbank.entity.type.TransactionType;
import com.goylik.cleverbank.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction create(Account receiver, Account sender, BigDecimal amount, TransactionType transactionType) throws ServiceException;
    Transaction getById(Long id) throws ServiceException;
    Transaction getByNumber(String number) throws ServiceException;
    List<Transaction> getAll() throws ServiceException;
    void deleteAll() throws ServiceException;
}
