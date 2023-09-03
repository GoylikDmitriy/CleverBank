package com.goylik.cleverbank.entity;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Bank extends BaseEntity {
    @NonNull private String name;
    @NonNull private String bankCode;
    private List<Account> accounts;
    private List<Transaction> transactionHistory;
}
