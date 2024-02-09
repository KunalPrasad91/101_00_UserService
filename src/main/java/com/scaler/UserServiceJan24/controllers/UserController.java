package com.scaler.UserServiceJan24.controllers;

import com.scaler.UserServiceJan24.dtos.*;
import com.scaler.UserServiceJan24.enums.ResponseStatus;
import com.scaler.UserServiceJan24.exceptions.PasswordNotMatchingException;
import com.scaler.UserServiceJan24.exceptions.TokenNotExistOrExpiredException;
import com.scaler.UserServiceJan24.exceptions.UserFoundException;
import com.scaler.UserServiceJan24.exceptions.UserNotFoundException;
import com.scaler.UserServiceJan24.models.Token;
import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.services.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private IUserServices userService;

    @Autowired
    UserController(IUserServices userService)
    {
        this.userService = userService;
    }

    @PostMapping()
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto)
    {
        System.out.println("User name " + userRequestDto.getName());
        System.out.println("User password " +  userRequestDto.getPassword());
        System.out.println("User phonenumber " + userRequestDto.getPhonenumber());
        System.out.println("User address " +  userRequestDto.getAddress());

        User userRequest = new User();
        userRequest.setName(userRequestDto.getName());
        userRequest.setHashedPassword(userRequestDto.getPassword());
        userRequest.setPhonenumber(userRequestDto.getPhonenumber());
        userRequest.setAddress(userRequestDto.getAddress());
        User createdUser = userService.createUser(userRequest);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(createdUser.getName());
        userResponseDto.setAddress(createdUser.getAddress());
        userResponseDto.setPhonenumber(createdUser.getPhonenumber());
        userResponseDto.setMessage("Succesfull");
        userResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return userResponseDto;
    }

    @PostMapping("/signup")
    ResponseEntity<UserResponseDto> signUpUser(@RequestBody UserRequestDto userRequestDto)
    {
        System.out.println("User name " + userRequestDto.getName());
        System.out.println("User email " + userRequestDto.getEmail());
        System.out.println("User password " +  userRequestDto.getPassword());
        System.out.println("User phonenumber " + userRequestDto.getPhonenumber());
        System.out.println("User address " +  userRequestDto.getAddress());

        User userRequest = new User();
        userRequest.setName(userRequestDto.getName());
        userRequest.setEmail(userRequestDto.getEmail());
        userRequest.setHashedPassword(userRequestDto.getPassword());
        userRequest.setPhonenumber(userRequestDto.getPhonenumber());
        userRequest.setAddress(userRequestDto.getAddress());

        ResponseEntity<UserResponseDto> responseEntity;
        UserResponseDto userResponseDto = new UserResponseDto();
        try {
            User createdUser = userService.signUpUser(userRequest);

            userResponseDto.setName(createdUser.getName());
            userResponseDto.setEmail(createdUser.getEmail());
            userResponseDto.setAddress(createdUser.getAddress());
            userResponseDto.setPhonenumber(createdUser.getPhonenumber());
            userResponseDto.setMessage("Succesfull");
            userResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

            responseEntity = new ResponseEntity<>(userResponseDto,HttpStatus.OK);
        }
        catch (UserFoundException e)
        {
            userResponseDto.setMessage(e.getMessage());
            userResponseDto.setResponseStatus(ResponseStatus.FAILURE);

            responseEntity = new ResponseEntity<>(userResponseDto, HttpStatus.NOT_ACCEPTABLE);
        }

        return responseEntity;
    }
/*

    @PostMapping("/login")
    ResponseEntity<UserResponseDto> userLogin(@RequestBody UserLoginRequestDto requestDto)
    {
        ResponseEntity<UserResponseDto> responseEntity;
        UserResponseDto response = new UserResponseDto();
        User savedUser;
        try {
            savedUser = userService.loginUser(requestDto.getEmail(), requestDto.getPassword());
            response.setEmail(savedUser.getEmail());
            response.setName(savedUser.getName());
            response.setPhonenumber(savedUser.getPhonenumber());
            response.setMessage("Logged In Succesfully");
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setAddress(savedUser.getAddress());
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(UserNotFoundException e)
        {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
            responseEntity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        catch (PasswordNotMatchingException e)
        {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
            responseEntity = new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
        }

        return  responseEntity;
    }
*/

    @PostMapping("/login")
    ResponseEntity<TokenResponseDto> userLogin(@RequestBody UserLoginRequestDto requestDto)
    {
        ResponseEntity<TokenResponseDto> responseEntity;
        UserResponseDto response = new UserResponseDto();

        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        try {
            Token token;
            token = userService.loginUser(requestDto.getEmail(), requestDto.getPassword());
            User savedUser = token.getUser();
            response.setEmail(savedUser.getEmail());
            response.setName(savedUser.getName());
            response.setPhonenumber(savedUser.getPhonenumber());
            response.setMessage("Logged In Succesfully");
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setAddress(savedUser.getAddress());
            tokenResponseDto.setUserResponseDto(response);
            tokenResponseDto.setMessage("Token generated succesfully");
            tokenResponseDto.setValue(token.getValue());
            tokenResponseDto.setExpirydate(token.getExpirydate());
            tokenResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseEntity = new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
        }
        catch(UserNotFoundException e)
        {
            tokenResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            tokenResponseDto.setMessage("Token not generated");
            tokenResponseDto.setUserResponseDto(response);
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
            responseEntity = new ResponseEntity<>(tokenResponseDto,HttpStatus.NOT_FOUND);
        }
        catch (PasswordNotMatchingException e)
        {
            tokenResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            tokenResponseDto.setMessage("Token not generated");
            tokenResponseDto.setUserResponseDto(response);
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
            responseEntity = new ResponseEntity<>(tokenResponseDto,HttpStatus.NOT_ACCEPTABLE);
        }

        return  responseEntity;
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> UserLogout(@RequestBody UserLogoutRequestDto request)
    {
        ResponseEntity<LogoutResponseDto> responseEntity;
        LogoutResponseDto response = new LogoutResponseDto();

        try {
            userService.logoutUser(request.getToken());
            response.setMessage("Logged out Succesfully");
            response.setResponseStatus(ResponseStatus.SUCCESS);
            responseEntity = new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(TokenNotExistOrExpiredException e)
        {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
            responseEntity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

 /*
    // Step 1 getUserById without ResponseEntity
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable("id") Long id)
    {
        UserResponseDto response = new UserResponseDto();

        User user;

        try {
            user = userService.getUserById(id);

            response.setName(user.getName());
            response.setPhonenumber(user.getPhonenumber());
            response.setAddress(user.getAddress());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("Found user by Id " + id);
        }
        catch (UserNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }

        return response;
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id)
    {
        UserResponseDto response = new UserResponseDto();
        ResponseEntity<UserResponseDto> responseEntity;
        User user;

        try {
            user = userService.getUserById(id);

            response.setName(user.getName());
            response.setPhonenumber(user.getPhonenumber());
            response.setAddress(user.getAddress());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("Found user by Id " + id);

            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch (UserNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);

            responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserResponseDto> getUserByName(@PathVariable("name") String name)
    {
        UserResponseDto response = new UserResponseDto();
        ResponseEntity<UserResponseDto> responseEntity;

        User user;

        try {
            user = userService.getUserByName(name);

            response.setName(user.getName());
            response.setPhonenumber(user.getPhonenumber());
            response.setAddress(user.getAddress());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("Found user by name " + name);

            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch (UserNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);

            responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return  responseEntity;
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getAllUsers()
    {
        ResponseEntity<List<UserResponseDto>> responseEntity;
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        List<User> users = userService.getAllUser();

        if(users.isEmpty())
        {
            responseEntity = new ResponseEntity<>(userResponseDtos,HttpStatus.NOT_FOUND);
        }
        else
        {
            for(User user :users)
            {
                UserResponseDto tempResponse = new UserResponseDto();
                tempResponse.setName(user.getName());
                tempResponse.setAddress(user.getAddress());
                tempResponse.setPhonenumber(user.getPhonenumber());
                tempResponse.setMessage("Successful");
                tempResponse.setResponseStatus(ResponseStatus.SUCCESS);

                userResponseDtos.add(tempResponse);
            }
            responseEntity = new ResponseEntity<>(userResponseDtos,HttpStatus.OK);
        }

        return responseEntity;
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id)
    {
        ResponseEntity<Void> responseEntity;

        userService.deleteUserById(id);

        responseEntity = new ResponseEntity<>(HttpStatus.OK);

        return responseEntity;
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteUserByName(@PathVariable("name") String name)
    {
        ResponseEntity<Void> responseEntity;

        userService.deleteUserByName(name);

        responseEntity = new ResponseEntity<>(HttpStatus.OK);

        return responseEntity;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable("id") Long id, @RequestBody UserRequestDto request)
    {
        UserResponseDto response = new UserResponseDto();
        ResponseEntity<UserResponseDto> responseEntity;

        System.out.println("User name " + request.getName());

        User user = new User();

        if(request.getName() != null)
            user.setName(request.getName());

        if(request.getPassword()!= null)
            user.setHashedPassword(request.getPassword());

        if (request.getAddress()!= null)
            user.setAddress(request.getAddress());

        if (request.getPhonenumber()!= null)
            user.setPhonenumber(request.getPhonenumber());

        try {
            user = userService.updateUserById(id,user);

            response.setName(user.getName());
            response.setAddress(user.getAddress());
            response.setPhonenumber(user.getPhonenumber());
            response.setMessage("Succesfully updated");
            response.setResponseStatus(ResponseStatus.SUCCESS);

            responseEntity = new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
            responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

}
