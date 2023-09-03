package com.goylik.cleverbank.repository.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * An interface representing a configuration for setting parameters in a PreparedStatement.
 */
public interface QueryConfig {
    /**
     * Sets parameters in the provided PreparedStatement.
     *
     * @param statement The PreparedStatement to set parameters for.
     * @throws SQLException If a database access error occurs.
     */
    void setParameters(PreparedStatement statement) throws SQLException;
}

