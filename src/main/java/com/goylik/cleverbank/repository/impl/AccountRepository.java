package com.goylik.cleverbank.repository.impl;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Bank;
import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.repository.BaseRepository;
import com.goylik.cleverbank.repository.config.impl.AccountQueryConfig;
import com.goylik.cleverbank.repository.exception.DalException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class AccountRepository extends BaseRepository<Account> {
    private final BankRepository bankRepository;
    private final UserRepository userRepository;

    private final String SQL_INSERT_ACCOUNT = "INSERT INTO cleverbank.account " +
            "(number, balance, bank_id, user_id) VALUES (?,?,?,?)";
    private final String SQL_SELECT_BY_NUMBER = "SELECT * FROM cleverbank.account WHERE number=?";
    private final String SQL_UPDATE_BALANCE = "UPDATE cleverbank.account SET balance =? WHERE number=?";

    public Account findByNumber(String number) throws DalException {
        try {
            Account account = null;
            List<Account> accounts = executeQuery(SQL_SELECT_BY_NUMBER, s -> s.setString(1, number));
            if (!accounts.isEmpty()) {
                account = accounts.get(0);
            }

            return account;
        }
        catch (SQLException e) {
            throw new DalException("Exception while selecting account by number.", e);
        }
    }

    @Override
    protected Account create(ResultSet resultSet) throws DalException {
        try {
            Long id = resultSet.getLong(1);
            String number = resultSet.getString(2);
            BigDecimal balance = resultSet.getBigDecimal(3);

            Long bankId = resultSet.getLong(4);
            Long userId = resultSet.getLong(5);
            Bank bank = this.bankRepository.findById(bankId);
            User user = this.userRepository.findById(userId);

            Account account = new Account(number, balance, bank, user);
            account.setId(id);
            return account;
        }
        catch (SQLException e) {
            throw new DalException("Exception while creating account.", e);
        }
    }

    @Override
    protected String getTableName() {
        return "cleverbank.account";
    }

    @Override
    public Account save(Account entity) throws DalException {
        try {
            Long id = executeUpdate(SQL_INSERT_ACCOUNT, new AccountQueryConfig(entity));
            entity.setId(id);
            return entity;
        }
        catch (SQLException e) {
            throw new DalException("Exception while inserting account.", e);
        }
    }

    public void updateBalance(Account account) throws DalException {
        try {
            executeUpdate(SQL_UPDATE_BALANCE, s -> {
                s.setBigDecimal(1, account.getBalance());
                s.setString(2, account.getNumber());
            });
        }
        catch (SQLException e) {
            throw new DalException("Exception while updating account balance.", e);
        }
    }
}
