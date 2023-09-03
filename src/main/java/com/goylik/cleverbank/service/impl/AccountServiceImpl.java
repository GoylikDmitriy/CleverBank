package com.goylik.cleverbank.service.impl;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Bank;
import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.entity.config.InterestCalculator;
import com.goylik.cleverbank.repository.exception.DalException;
import com.goylik.cleverbank.repository.impl.AccountRepository;
import com.goylik.cleverbank.service.AccountService;
import com.goylik.cleverbank.service.exception.InsufficientBalanceException;
import com.goylik.cleverbank.service.exception.ServiceException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Providing methods to manage account-related operations.
 */
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    /**
     * Opens a new account for a user at a specified bank with an initial fee.
     *
     * @param bank The bank where the account is being opened.
     * @param user The user for whom the account is being opened.
     * @param initialFee  The initial fee for opening the account.
     * @return The account number of the newly opened account.
     * @throws ServiceException If an error occurs during account opening or saving.
     */
    @Override
    public String openAccount(Bank bank, User user, BigDecimal initialFee) throws ServiceException {
        try {
            String number = UUID.randomUUID().toString();
            Account account = new Account(number, initialFee, bank, user);
            account.subscribe(new InterestCalculator(account));
            this.accountRepository.save(account);
            return account.getNumber();
        } catch (DalException e) {
            throw new ServiceException("Can't open account.", e);
        }
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param accountId The ID of the account to retrieve.
     * @return The retrieved account.
     * @throws ServiceException If an error occurs while retrieving the account.
     */
    @Override
    public Account getById(Long accountId) throws ServiceException {
        try {
            return this.accountRepository.findById(accountId);
        } catch (DalException e) {
            throw new ServiceException("Can't find account by id.", e);
        }
    }

    /**
     * Retrieves an account by its account number.
     *
     * @param accountNumber The account number of the account to retrieve.
     * @return The retrieved account.
     * @throws ServiceException If an error occurs while retrieving the account.
     */
    @Override
    public Account getByNumber(String accountNumber) throws ServiceException {
        try {
            return this.accountRepository.findByNumber(accountNumber);
        } catch (DalException e) {
            throw new ServiceException("Can't find account by number.", e);
        }
    }

    /**
     * Retrieves all accounts.
     *
     * @return A list of all accounts.
     * @throws ServiceException If an error occurs while retrieving all accounts.
     */
    @Override
    public List<Account> getAll() throws ServiceException {
        try {
            return this.accountRepository.findAll();
        } catch (DalException e) {
            throw new ServiceException("Can't find all accounts.", e);
        }
    }

    /**
     * Adds an amount to the balance of a specified account.
     *
     * @param accountNumber The account number of the account to update.
     * @param amount The amount to add to the account's balance.
     * @throws ServiceException If an error occurs while updating the account balance.
     */
    @Override
    public void addToBalance(String accountNumber, BigDecimal amount) throws ServiceException {
        try {
            Account account = this.accountRepository.findByNumber(accountNumber);
            BigDecimal currentBalance = account.getBalance();
            account.setBalance(currentBalance.add(amount));
            this.accountRepository.updateBalance(account);
        } catch (DalException e) {
            throw new ServiceException("Can't add to balance.", e);
        }
    }

    /**
     * Subtracts an amount from the balance of a specified account.
     *
     * @param accountNumber The account number of the account to update.
     * @param amount The amount to subtract from the account's balance.
     * @throws ServiceException If an error occurs while updating the account balance or if the balance is insufficient.
     */
    @Override
    public void subtractFromBalance(String accountNumber, BigDecimal amount) throws ServiceException {
        try {
            Account account = this.accountRepository.findByNumber(accountNumber);
            BigDecimal currentBalance = account.getBalance();
            if (currentBalance.compareTo(amount) < 0) {
                throw new InsufficientBalanceException("Insufficient balance.");
            }

            account.setBalance(currentBalance.subtract(amount));
            this.accountRepository.updateBalance(account);
        } catch (DalException e) {
            throw new ServiceException("Can't subtract from balance.", e);
        }
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            this.accountRepository.deleteAll();
        }
        catch (DalException e) {
            throw new ServiceException("Can't delete all accounts.", e);
        }
    }
}
