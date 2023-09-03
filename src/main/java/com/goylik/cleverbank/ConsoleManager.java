package com.goylik.cleverbank;

import com.goylik.cleverbank.controller.ControllerFacade;
import com.goylik.cleverbank.entity.User;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static User loggedInUser;

    public static void main(String[] args) {
        ControllerFacade.updateSubscribes();
        while (true) {
            if (loggedInUser == null) {
                System.out.println("Выберите действие:");
                System.out.println("1. Регистрация");
                System.out.println("2. Логин");
                System.out.println("3. Выход");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> registerUser();
                    case 2 -> loggedInUser = loginUser();
                    case 3 -> {
                        System.out.println("Выход из программы.");
                        System.exit(0);
                    }
                    default -> System.out.println("Некорректный выбор. Пожалуйста, выберите действие 1, 2 или 3.");
                }
            } else {
                System.out.println("Выберите действие:");
                System.out.println("1. Депозит");
                System.out.println("2. Вывод");
                System.out.println("3. Перевод");
                System.out.println("4. Открыть счет");
                System.out.println("5. Выйти из учетной записи");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> deposit();
                    case 2 -> withdraw();
                    case 3 -> transfer();
                    case 4 -> openAccount();
                    case 5 -> {
                        loggedInUser = null;
                        System.out.println("Выход из учетной записи.");
                    }
                    default -> System.out.println("Некорректный выбор. Пожалуйста, выберите действие 1, 2, 3, 4 или 5.");
                }
            }
        }
    }

    private static void registerUser() {
        String firstName, lastName, email, phoneNumber, password;
        System.out.println("Введите имя: ");
        firstName = scanner.next();
        System.out.println("Введите фамилию: ");
        lastName = scanner.next();
        System.out.println("Введите почту: ");
        email = scanner.next();
        System.out.println("Введите номер телефона: ");
        phoneNumber = scanner.next();
        System.out.println("Введите пароль: ");
        password = scanner.next();

        ControllerFacade.register(new User(firstName, lastName, email, phoneNumber, password));
    }

    private static User loginUser() {
        String phoneNumber, password;
        System.out.println("Введите номер телефона: ");
        phoneNumber = scanner.next();
        System.out.println("Введите пароль: ");
        password = scanner.next();

        return ControllerFacade.login(new User(phoneNumber, password));
    }

    private static void deposit() {
        String accountNumber;
        BigDecimal amount;
        System.out.println("Номер счета для пополнения: ");
        accountNumber = scanner.next();
        System.out.println("Сумма пополнения: ");
        amount = scanner.nextBigDecimal();
        ControllerFacade.deposit(accountNumber, amount);
    }

    private static void withdraw() {
        String accountNumber;
        BigDecimal amount;
        System.out.println("Номер счета для вывода: ");
        accountNumber = scanner.next();
        System.out.println("Сумма вывода: ");
        amount = scanner.nextBigDecimal();
        ControllerFacade.withdraw(accountNumber, amount);
    }

    private static void transfer() {
        String receiverNumber, senderNumber;
        BigDecimal amount;
        System.out.println("Номер счета отрпавителя: ");
        senderNumber = scanner.next();
        System.out.println("Номер счета получателя: ");
        receiverNumber = scanner.next();
        System.out.println("Сумма перевода: ");
        amount = scanner.nextBigDecimal();
        ControllerFacade.transfer(receiverNumber, senderNumber, amount);
    }

    private static void openAccount() {
        BigDecimal initialFee;
        System.out.println("Введите первоначальный взнос: ");
        initialFee = scanner.nextBigDecimal();
        ControllerFacade.openAccount("CLV", loggedInUser, initialFee);
    }
}
