package com.scaler.UserServiceJan24.controllers;

import com.scaler.UserServiceJan24.dtos.UserDto;
import com.scaler.UserServiceJan24.dtos.UserRequestDto;
import com.scaler.UserServiceJan24.dtos.UserResponseDto;
import com.scaler.UserServiceJan24.enums.ResponseStatus;
import com.scaler.UserServiceJan24.exceptions.UserNotFoundException;
import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.services.IUserServices;
import com.scaler.UserServiceJan24.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
