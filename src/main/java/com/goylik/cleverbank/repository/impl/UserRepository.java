package com.goylik.cleverbank.repository.impl;

import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.repository.BaseRepository;
import com.goylik.cleverbank.repository.config.impl.UserQueryConfig;
import com.goylik.cleverbank.repository.exception.DalException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepository extends BaseRepository<User> {
    private final String SQL_INSERT_USER = "INSERT INTO cleverbank.user " +
            "(first_name, last_name, phone_number, email, password) VALUES (?,?,?,?,?)";
    private final String SQL_SELECT_BY_PHONE_NUMBER = "SELECT * FROM cleverbank.user WHERE phone_number=?";

    public User findByPhoneNumber(String phoneNumber) throws DalException {
        try {
            User user = null;
             List<User> users = executeQuery(SQL_SELECT_BY_PHONE_NUMBER, s -> s.setString(1, phoneNumber));
             if (!users.isEmpty()) {
                 user = users.get(0);
             }

             return user;
        }
        catch (SQLException e) {
            throw new DalException("Exception while selecting user by phone number.", e);
        }
    }

    @Override
    protected User create(ResultSet resultSet) throws DalException {
        try {
            Long id = resultSet.getLong(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String phoneNumber = resultSet.getString(4);
            String email = resultSet.getString(5);
            String password = resultSet.getString(6);

            User user = new User(firstName, lastName, phoneNumber, email, password);
            user.setId(id);
            return user;
        }
        catch (SQLException e) {
            throw new DalException("Exception while creating user.", e);
        }
    }

    @Override
    protected String getTableName() {
        return "cleverbank.user";
    }

    @Override
    public User save(User entity) throws DalException {
        try {
            Long id = executeUpdate(SQL_INSERT_USER, new UserQueryConfig(entity));
            entity.setId(id);
            return entity;
        }
        catch (SQLException e) {
            throw new DalException("Exception while inserting user.", e);
        }
    }
}
