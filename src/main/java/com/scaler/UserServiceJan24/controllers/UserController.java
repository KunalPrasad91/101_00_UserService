package com.scaler.UserServiceJan24.controllers;

import com.scaler.UserServiceJan24.dtos.UserDto;
import com.scaler.UserServiceJan24.dtos.UserRequestDto;
import com.scaler.UserServiceJan24.dtos.UserResponseDto;
import com.scaler.UserServiceJan24.enums.ResponseStatus;
import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.services.IUserServices;
import com.scaler.UserServiceJan24.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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



}
