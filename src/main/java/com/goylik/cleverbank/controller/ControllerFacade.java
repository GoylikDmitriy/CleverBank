package com.goylik.cleverbank.controller;

import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.repository.impl.AccountRepository;
import com.goylik.cleverbank.repository.impl.BankRepository;
import com.goylik.cleverbank.repository.impl.TransactionRepository;
import com.goylik.cleverbank.repository.impl.UserRepository;
import com.goylik.cleverbank.service.AccountService;
import com.goylik.cleverbank.service.BankService;
import com.goylik.cleverbank.service.TransactionService;
import com.goylik.cleverbank.service.UserService;
import com.goylik.cleverbank.service.impl.AccountServiceImpl;
import com.goylik.cleverbank.service.impl.BankServiceImpl;
import com.goylik.cleverbank.service.impl.TransactionServiceImpl;
import com.goylik.cleverbank.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public final class ControllerFacade {
    static UserController userController;
    static AccountController accountController;


    static {
        UserRepository userRepository = new UserRepository();
        BankRepository bankRepository = new BankRepository();
        AccountRepository accountRepository = new AccountRepository(bankRepository, userRepository);
        TransactionRepository transactionRepository = new TransactionRepository(accountRepository);

        UserService userService = new UserServiceImpl(userRepository);
        BankService bankService = new BankServiceImpl(bankRepository);
        AccountService accountService = new AccountServiceImpl(accountRepository);
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository);

        userController = new UserController(userService);
        accountController = new AccountController(accountService, bankService, transactionService);
    }

    public static void register(User user) {
        userController.register(user);
    }

    public static User login(User user) {
        return userController.login(user);
    }

    public static void openAccount(String bankCode, User user, BigDecimal initialFee) {
        accountController.openAccount(bankCode, user, initialFee);
    }

    public static void deposit(String accountNumber, BigDecimal amount) {
        accountController.deposit(accountNumber, amount);
    }

    public static void withdraw(String accountNumber, BigDecimal amount) {
        accountController.withdraw(accountNumber, amount);
    }

    public static void transfer(String receiverAccountNumber, String senderAccountNumber, BigDecimal amount) {
        accountController.transfer(receiverAccountNumber, senderAccountNumber, amount);
    }

    public static void updateSubscribes() {
        accountController.updateSubscribes();
    }
}
