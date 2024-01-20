package com.scaler.UserServiceJan24.services;

import com.scaler.UserServiceJan24.exceptions.UserNotFoundException;
import com.scaler.UserServiceJan24.models.User;

public interface IUserServices {

    public User createUser(User user);
    public User getUserById(Long id) throws UserNotFoundException;
}
