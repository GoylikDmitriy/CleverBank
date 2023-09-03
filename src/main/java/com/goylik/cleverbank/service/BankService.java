package com.goylik.cleverbank.service;

import com.goylik.cleverbank.entity.Bank;
import com.goylik.cleverbank.service.exception.ServiceException;

import java.util.List;

public interface BankService {
    Bank save(Bank bank) throws ServiceException;
    Bank getById(Long id) throws ServiceException;
    Bank getByCode(String bankCode) throws ServiceException;
    List<Bank> getAll() throws ServiceException;
    void deleteAll() throws ServiceException;
}
