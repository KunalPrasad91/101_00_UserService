package com.scaler.UserServiceJan24.services;

import com.scaler.UserServiceJan24.exceptions.PasswordNotMatchingException;
import com.scaler.UserServiceJan24.exceptions.TokenNotExistOrExpiredException;
import com.scaler.UserServiceJan24.exceptions.UserFoundException;
import com.scaler.UserServiceJan24.exceptions.UserNotFoundException;
import com.scaler.UserServiceJan24.models.Token;
import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.repositories.ITokenRepository;
import com.scaler.UserServiceJan24.repositories.IUserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices implements  IUserServices{

    private IUserRepository userRepository;
    private ITokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserServices(IUserRepository userRepository,
                 ITokenRepository tokenRepository,
                 BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public User createUser(User userRequest)
    {
        User savedUser = userRepository.save(userRequest);

        return savedUser;
    }

    @Override
    public User signUpUser(User userRequest) throws UserFoundException {
    // User signUpUser(String name, String email, String password)
        String email = userRequest.getEmail();

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(!userOptional.isEmpty())
        {
            throw new UserFoundException("User with " + email + " already exist");
        }

        userRequest.setHashedPassword(bCryptPasswordEncoder.encode(userRequest.getHashedPassword()));
        User savedUser = userRepository.save(userRequest);

        return savedUser;
    }

    @Override
    public Token loginUser(String email, String password) throws UserNotFoundException, PasswordNotMatchingException {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty())
        {
            throw  new UserNotFoundException("User with email " + email + " doesnot exist");
        }

        User savedUser = optionalUser.get();

         /*   if(!password.equals(savedUser.getHashedPassword()))
        {
            throw  new PasswordNotMatchingException("Incorrect password, Login Failed, 3 more attempt left");
        }*/

        if(!bCryptPasswordEncoder.matches(password, savedUser.getHashedPassword()))
        {
            throw  new PasswordNotMatchingException("Incorrect password, Login Failed, 3 more attempt left");
        }

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);

        // Convert LocalDate to Date
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpirydate(expiryDate);
        token.setUser(savedUser);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setDeleted(false);
        token = tokenRepository.save(token);

        return token;
    }

    public Void logoutUser(String token) throws TokenNotExistOrExpiredException {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(token,false);

        if(tokenOptional.isEmpty())
        {
            throw new TokenNotExistOrExpiredException("Token Not Exist Or Expired Exception");
        }

        Token tkn = tokenOptional.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);

        return null;
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

    public List<User> getAllUser()
    {
        return  userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        return;
    }

    @Override
    public void deleteUserByName(String name) {
        userRepository.deleteByName(name);
        return;
    }

    @Override
    public User updateUserById(Long id, User request) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
        {
            throw  new UserNotFoundException("User with id " + id + " not found");
        }

        User savedUser = userOptional.get();

        if(request.getName() != null)
            savedUser.setName(request.getName());

        if(request.getHashedPassword()!= null)
            savedUser.setHashedPassword(request.getHashedPassword());

        if (request.getAddress()!= null)
            savedUser.setAddress(request.getAddress());

        if(request.getPhonenumber()!= null)
            savedUser.setPhonenumber(request.getPhonenumber());

        savedUser = userRepository.save(savedUser);

        return  savedUser;
    }
}
