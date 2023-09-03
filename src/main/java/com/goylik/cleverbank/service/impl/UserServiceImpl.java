package com.goylik.cleverbank.service.impl;

import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.repository.exception.DalException;
import com.goylik.cleverbank.repository.impl.UserRepository;
import com.goylik.cleverbank.service.UserService;
import com.goylik.cleverbank.service.exception.IncorrectPasswordException;
import com.goylik.cleverbank.service.exception.ServiceException;
import com.goylik.cleverbank.service.exception.UserAlreadyExistException;
import com.goylik.cleverbank.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Providing methods to manage user-related operations.
 */
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Registers a new user.
     *
     * @param user The user to be registered.
     * @throws ServiceException If an error occurs during user registration.
     */
    @Override
    public void register(User user) throws ServiceException {
        try {
            String phoneNumber = user.getPhoneNumber();
            User userByPhoneNumber = this.userRepository.findByPhoneNumber(phoneNumber);
            if (userByPhoneNumber != null) {
                throw new UserAlreadyExistException("User with this phone number already exists.");
            }

            this.userRepository.save(user);
        } catch (DalException e) {
            throw new ServiceException("Exception while register user.");
        }
    }

    /**
     * Logs in a user.
     *
     * @param user The user trying to log in.
     * @return The logged-in user.
     * @throws ServiceException If an error occurs during user login.
     */
    @Override
    public User login(User user) throws ServiceException {
        try {
            String phoneNumber = user.getPhoneNumber();
            User userByPhoneNumber = this.userRepository.findByPhoneNumber(phoneNumber);
            if (userByPhoneNumber == null) {
                throw new UserNotFoundException("User with phone number " + phoneNumber + " not found.");
            }

            if (user.getPassword().equals(userByPhoneNumber.getPassword())) {
                return userByPhoneNumber;
            } else {
                throw new IncorrectPasswordException("Incorrect password.");
            }
        } catch (DalException e) {
            throw new ServiceException("Error during user login.", e);
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The retrieved user.
     * @throws ServiceException If an error occurs while retrieving the user.
     */
    @Override
    public User getById(Long id) throws ServiceException {
        try {
            return this.userRepository.findById(id);
        } catch (DalException e) {
            throw new ServiceException("Can't find user by id.", e);
        }
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     * @throws ServiceException If an error occurs while retrieving all users.
     */
    @Override
    public List<User> getAll() throws ServiceException {
        try {
            return this.userRepository.findAll();
        } catch (DalException e) {
            throw new ServiceException("Can't find all users.", e);
        }
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            this.userRepository.deleteAll();
        }
        catch (DalException e) {
            throw new ServiceException("Can't delete all users.", e);
        }
    }
}