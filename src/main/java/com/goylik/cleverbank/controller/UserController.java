package com.goylik.cleverbank.controller;

import com.goylik.cleverbank.entity.User;
import com.goylik.cleverbank.service.UserService;
import com.goylik.cleverbank.service.exception.ServiceException;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserController {
    private final UserService userService;

    public void register(User user) {
        try {
            this.userService.register(user);
        }
        catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public User login(User user) {
        try {
            return this.userService.login(user);
        }
        catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }
}
