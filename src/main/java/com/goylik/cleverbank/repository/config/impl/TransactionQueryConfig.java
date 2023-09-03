package com.goylik.cleverbank.repository.config.impl;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Transaction;
import com.goylik.cleverbank.repository.config.QueryConfig;
import lombok.AllArgsConstructor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static java.sql.Types.BIGINT;

/**
 * A configuration class for setting parameters related to a Transaction in a PreparedStatement.
 */
@AllArgsConstructor
public class TransactionQueryConfig implements QueryConfig {
    private final Transaction transaction;

    /**
     * Sets parameters related to the Transaction in the provided PreparedStatement.
     *
     * @param statement The PreparedStatement to set Transaction-related parameters for.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void setParameters(PreparedStatement statement) throws SQLException {
        Account receiver = transaction.getReceiver();
        Account sender = transaction.getSender();

        statement.setString(1, transaction.getNumber());

        if (receiver == null) {
            statement.setNull(2, Types.BIGINT);
        } else {
            statement.setLong(2, receiver.getId());
        }

        if (sender == null) {
            statement.setNull(3, Types.BIGINT);
        } else {
            statement.setLong(3, sender.getId());
        }

        statement.setBigDecimal(4, transaction.getAmount());
        statement.setString(5, transaction.getTransactionType().getType());
        statement.setDate(6, Date.valueOf(transaction.getTransactionDate()));
    }

}

