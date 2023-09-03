package com.goylik.cleverbank.service.impl;

import com.goylik.cleverbank.entity.Bank;
import com.goylik.cleverbank.repository.exception.DalException;
import com.goylik.cleverbank.repository.impl.BankRepository;
import com.goylik.cleverbank.service.BankService;
import com.goylik.cleverbank.service.exception.ServiceException;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Providing methods to manage bank-related operations.
 */
@AllArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    /**
     * Saves a new bank or updates an existing bank.
     *
     * @param bank The bank to be saved or updated.
     * @throws ServiceException If an error occurs during bank saving or updating.
     */
    @Override
    public Bank save(Bank bank) throws ServiceException {
        try {
            return this.bankRepository.save(bank);
        } catch (DalException e) {
            throw new ServiceException("Can't save bank.", e);
        }
    }

    /**
     * Retrieves a bank by its ID.
     *
     * @param id The ID of the bank to retrieve.
     * @return The retrieved bank.
     * @throws ServiceException If an error occurs while retrieving the bank.
     */
    @Override
    public Bank getById(Long id) throws ServiceException {
        try {
            return this.bankRepository.findById(id);
        } catch (DalException e) {
            throw new ServiceException("Can't find bank by id.", e);
        }
    }

    /**
     * Retrieves a bank by its code.
     *
     * @param bankCode The code of the bank to retrieve.
     * @return The retrieved bank.
     * @throws ServiceException If an error occurs while retrieving the bank.
     */
    @Override
    public Bank getByCode(String bankCode) throws ServiceException {
        try {
            return this.bankRepository.findByCode(bankCode);
        } catch (DalException e) {
            throw new ServiceException("Can't find bank by code.", e);
        }
    }

    /**
     * Retrieves all banks.
     *
     * @return A list of all banks.
     * @throws ServiceException If an error occurs while retrieving all banks.
     */
    @Override
    public List<Bank> getAll() throws ServiceException {
        try {
            return this.bankRepository.findAll();
        } catch (DalException e) {
            throw new ServiceException("Can't find all banks.", e);
        }
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            this.bankRepository.deleteAll();
        }
        catch (DalException e) {
            throw new ServiceException("Can't delete all banks.", e);
        }
    }
}

