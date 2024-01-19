package com.scaler.UserServiceJan24.controllers;

import com.scaler.UserServiceJan24.dtos.UserDto;
import com.scaler.UserServiceJan24.models.User;
import com.scaler.UserServiceJan24.services.IUserServices;
import com.scaler.UserServiceJan24.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDto createUser(@RequestBody User userRequest)
    {
        System.out.println("User name " + userRequest.getName());
        System.out.println("User password " +  userRequest.getPassword());

/*        User request = new User();
        request.setName(userRequest.getName());
        request.setPassword(userRequest.getPassword());*/
        User createdUser = userService.createUser(userRequest);

        UserDto userDto = new UserDto();
        userDto.setName(createdUser.getName());
        userDto.setMessage("Succesfull");

        return userDto;
    }

}
