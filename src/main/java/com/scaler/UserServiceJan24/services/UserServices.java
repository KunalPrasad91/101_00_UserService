package com.scaler.UserServiceJan24.services;

import com.scaler.UserServiceJan24.exceptions.UserNotFoundException;
import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public User getUserById(Long id) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        return userOptional.get();
    }

    public User getUserByName(String name) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByName(name);

        if(userOptional.isEmpty())
        {
            throw  new UserNotFoundException("User with name " + name + " not found");
        }

        return userOptional.get();

    }

}
