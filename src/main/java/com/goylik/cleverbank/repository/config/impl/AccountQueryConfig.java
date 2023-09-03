package com.goylik.cleverbank.repository.config.impl;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.repository.config.QueryConfig;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A configuration class for setting parameters related to an Account in a PreparedStatement.
 */
@AllArgsConstructor
public class AccountQueryConfig implements QueryConfig {
    private final Account account;

    /**
     * Sets parameters related to the Account in the provided PreparedStatement.
     *
     * @param statement The PreparedStatement to set Account-related parameters for.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void setParameters(PreparedStatement statement) throws SQLException {
        statement.setString(1, account.getNumber());
        statement.setBigDecimal(2, account.getBalance());
        statement.setLong(3, account.getBank().getId());
        statement.setLong(4, account.getUser().getId());
    }
}
