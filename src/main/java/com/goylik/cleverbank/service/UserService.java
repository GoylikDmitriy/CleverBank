package com.goylik.cleverbank.service;

import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    void register(User user) throws ServiceException;
    User login(User user) throws ServiceException;
    User getById(Long id) throws ServiceException;
    List<User> getAll() throws ServiceException;
    void deleteAll() throws ServiceException;
}