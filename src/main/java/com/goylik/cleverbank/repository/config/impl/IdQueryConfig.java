package com.goylik.cleverbank.repository.config.impl;

import com.goylik.cleverbank.repository.config.QueryConfig;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A configuration class for setting the ID parameter in a PreparedStatement.
 */
@AllArgsConstructor
public class IdQueryConfig implements QueryConfig {
    private final Long id;

    /**
     * Sets the ID parameter in the provided PreparedStatement.
     *
     * @param statement The PreparedStatement to set the ID parameter for.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void setParameters(PreparedStatement statement) throws SQLException {
        statement.setLong(1, id);
    }
}

