package com.goylik.cleverbank.entity;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User extends BaseEntity {
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String phoneNumber;
    @NonNull private String email;
    @NonNull private String password;
    private List<Account> accounts;
    private List<Transaction> transactionHistory;

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
