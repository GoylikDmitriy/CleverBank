package com.goylik.cleverbank.repository.config.impl;

import com.goylik.cleverbank.entity.Bank;
import com.goylik.cleverbank.repository.config.QueryConfig;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A configuration class for setting parameters related to a Bank in a PreparedStatement.
 */
@AllArgsConstructor
public class BankQueryConfig implements QueryConfig {
    private final Bank bank;

    /**
     * Sets parameters related to the Bank in the provided PreparedStatement.
     *
     * @param statement The PreparedStatement to set Bank-related parameters for.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void setParameters(PreparedStatement statement) throws SQLException {
        statement.setString(1, bank.getName());
        statement.setString(2, bank.getBankCode());
    }
}

