package com.scaler.UserServiceJan24.services;

import com.scaler.UserServiceJan24.exceptions.UserFoundException;
import com.scaler.UserServiceJan24.exceptions.UserNotFoundException;
import com.scaler.UserServiceJan24.models.User;

import java.util.List;

public interface IUserServices {

    public User createUser(User user);
    public User signUpUser(User user) throws UserFoundException;
    public User getUserById(Long id) throws UserNotFoundException;
    public User getUserByName(String name) throws UserNotFoundException;
    public List<User> getAllUser();

    public void deleteUserById(Long id);
    public void deleteUserByName(String name);

    public User updateUserById(Long id, User user) throws UserNotFoundException;
}
