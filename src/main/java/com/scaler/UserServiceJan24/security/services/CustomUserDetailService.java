package com.scaler.UserServiceJan24.security.services;

import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.repositories.IUserRepository;

import com.scaler.UserServiceJan24.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private IUserRepository userRepository;

    public CustomUserDetailService(IUserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByEmail(username);

        if(userOptional.isEmpty())
        {
            throw new UsernameNotFoundException("User with email " + username + " dont exist");
        }

        CustomUserDetails userDetail = new CustomUserDetails(userOptional.get());

        return userDetail;
    }
}
