package com.scaler.UserServiceJan24.controllers;

import com.scaler.UserServiceJan24.dtos.UserDto;
import com.scaler.UserServiceJan24.dtos.UserRequestDto;
import com.scaler.UserServiceJan24.dtos.UserResponseDto;
import com.scaler.UserServiceJan24.enums.ResponseStatus;
import com.scaler.UserServiceJan24.exceptions.UserFoundException;
import com.scaler.UserServiceJan24.exceptions.UserNotFoundException;
import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.services.IUserServices;
import com.scaler.UserServiceJan24.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
        userRequest.setPassword(userRequestDto.getPassword());
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
    UserResponseDto signUpUser(@RequestBody UserRequestDto userRequestDto)
    {
        System.out.println("User name " + userRequestDto.getName());
        System.out.println("User email " + userRequestDto.getEmail());
        System.out.println("User password " +  userRequestDto.getPassword());
        System.out.println("User phonenumber " + userRequestDto.getPhonenumber());
        System.out.println("User address " +  userRequestDto.getAddress());

        User userRequest = new User();
        userRequest.setName(userRequestDto.getName());
        userRequest.setEmail(userRequestDto.getEmail());
        userRequest.setPassword(userRequestDto.getPassword());
        userRequest.setPhonenumber(userRequestDto.getPhonenumber());
        userRequest.setAddress(userRequestDto.getAddress());

        UserResponseDto userResponseDto = new UserResponseDto();
        try {
            User createdUser = userService.signUpUser(userRequest);

            userResponseDto.setName(createdUser.getName());
            userResponseDto.setEmail(createdUser.getEmail());
            userResponseDto.setAddress(createdUser.getAddress());
            userResponseDto.setPhonenumber(createdUser.getPhonenumber());
            userResponseDto.setMessage("Succesfull");
            userResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (UserFoundException e)
        {
            userResponseDto.setMessage(e.getMessage());
            userResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return userResponseDto;
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
            user.setPassword(request.getPassword());

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
