package com.goylik.cleverbank.controller;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Bank;
import com.goylik.cleverbank.entity.Transaction;
import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.entity.config.InterestCalculator;
import com.goylik.cleverbank.entity.type.TransactionType;
import com.goylik.cleverbank.service.AccountService;
import com.goylik.cleverbank.service.BankService;
import com.goylik.cleverbank.service.TransactionService;
import com.goylik.cleverbank.service.exception.ServiceException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final BankService bankService;
    private final TransactionService transactionService;

    public void openAccount(String bankCode, User user, BigDecimal initialFee) {
        try {
            Bank bank = this.bankService.getByCode(bankCode);
            this.accountService.openAccount(bank, user, initialFee);
        }
        catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        try {
            Account receiver = this.accountService.getByNumber(accountNumber);
            this.accountService.addToBalance(accountNumber, amount);
            Transaction transaction = this.transactionService.create(receiver, null, amount, TransactionType.DEPOSIT);
        }
        catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        try {
            Account sender = this.accountService.getByNumber(accountNumber);
            this.accountService.subtractFromBalance(accountNumber, amount);
            Transaction transaction = this.transactionService.create(null, sender, amount, TransactionType.WITHDRAW);
        }
        catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void transfer(String receiverAccountNumber, String senderAccountNumber, BigDecimal amount) {
        try {
            Account receiver = this.accountService.getByNumber(receiverAccountNumber);
            Account sender = this.accountService.getByNumber(senderAccountNumber);
            this.accountService.addToBalance(receiverAccountNumber, amount);
            this.accountService.subtractFromBalance(senderAccountNumber, amount);
            Transaction transaction = this.transactionService.create(receiver, sender, amount, TransactionType.TRANSFER);
        }
        catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void updateSubscribes() {
        try {
            List<Account> accounts = this.accountService.getAll();
            for (Account account : accounts) {
                account.subscribe(new InterestCalculator(account));
            }
        }
        catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
