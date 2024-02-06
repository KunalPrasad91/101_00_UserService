package com.scaler.UserServiceJan24.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String name;
    private String email;
    private String password;
    private String phonenumber;
    private String address;

}
