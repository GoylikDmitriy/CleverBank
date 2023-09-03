package com.goylik.cleverbank.repository.impl;

import com.goylik.cleverbank.entity.Bank;
import com.goylik.cleverbank.repository.BaseRepository;
import com.goylik.cleverbank.repository.config.impl.BankQueryConfig;
import com.goylik.cleverbank.repository.exception.DalException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BankRepository extends BaseRepository<Bank> {
    private final String SQL_INSERT_BANK = "INSERT INTO cleverbank.bank " +
            "(name, bank_code) VALUES (?,?) RETURNING id";
    private final String SQL_SELECT_BY_CODE = "SELECT * FROM cleverbank.bank WHERE bank_code=?";

    public Bank findByCode(String bankCode) throws DalException {
        try {
            Bank bank = null;
            List<Bank> banks = executeQuery(SQL_SELECT_BY_CODE, s -> s.setString(1, bankCode));
            if (!banks.isEmpty()) {
                bank = banks.get(0);
            }

            return bank;
        }
        catch (SQLException e) {
            throw new DalException("Exception while selecting bank by code.", e);
        }
    }

    @Override
    protected Bank create(ResultSet resultSet) throws DalException {
        try {
            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            String bankCode = resultSet.getString(3);

            Bank bank = new Bank(name, bankCode);
            bank.setId(id);
            return bank;
        }
        catch (SQLException e) {
            throw new DalException("Exception while creating bank.", e);
        }
    }

    @Override
    protected String getTableName() {
        return "cleverbank.bank";
    }

    @Override
    public Bank save(Bank entity) throws DalException {
        try {
            Long id = executeUpdate(SQL_INSERT_BANK, new BankQueryConfig(entity));
            entity.setId(id);
            return entity;
        }
        catch (SQLException e) {
            throw new DalException("Exception while inserting bank.", e);
        }
    }
}
