package com.goylik.cleverbank.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TransactionType {
    DEPOSIT("Пополнение"), WITHDRAW("Вывод"), TRANSFER("Перевод");

    @Getter private final String type;

    public static TransactionType toType(String type) {
        return switch (type) {
            case "Пополнение" -> DEPOSIT;
            case "Вывод" -> WITHDRAW;
            case "Перевод" -> TRANSFER;
            default -> null;
        };
    }
}
