package com.goylik.cleverbank.entity;

import com.goylik.cleverbank.entity.config.Observer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Account extends BaseEntity {
    @NonNull private String number;
    @NonNull private BigDecimal balance;
    @NonNull private Bank bank;
    @NonNull private User user;

    private final List<Observer> subscribes = new ArrayList<>();

    public void subscribe(Observer observer) {
        this.subscribes.add(observer);
    }

    public void unsubscribe(Observer observer) {
        this.subscribes.remove(observer);
    }
}