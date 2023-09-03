package com.goylik.cleverbank.entity;

import com.goylik.cleverbank.entity.type.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Transaction extends BaseEntity {
    @NonNull private String number;
    private Account receiver;
    private Account sender;
    @NonNull private BigDecimal amount;
    @NonNull private TransactionType transactionType;
    @NonNull private LocalDate transactionDate;
}
