package com.goylik.cleverbank.repository.config.impl;

import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.repository.config.QueryConfig;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A configuration class for setting parameters related to a User in a PreparedStatement.
 */
@AllArgsConstructor
public class UserQueryConfig implements QueryConfig {
    private final User user;

    /**
     * Sets parameters related to the User in the provided PreparedStatement.
     *
     * @param statement The PreparedStatement to set User-related parameters for.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void setParameters(PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getPhoneNumber());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPassword());
    }
}

