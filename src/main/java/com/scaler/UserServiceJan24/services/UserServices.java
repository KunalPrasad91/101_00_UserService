package com.scaler.UserServiceJan24.services;

import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements  IUserServices{

    IUserRepository userRepository;

    @Autowired
    UserServices(IUserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    public User createUser(User userRequest)
    {
        User savedUser = userRepository.save(userRequest);

        return savedUser;
    }

}
