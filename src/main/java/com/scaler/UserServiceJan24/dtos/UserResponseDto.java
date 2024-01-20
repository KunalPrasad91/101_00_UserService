package com.scaler.UserServiceJan24.dtos;

import com.scaler.UserServiceJan24.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private String name;
    private String phonenumber;
    private String address;
    private String message;
    private ResponseStatus responseStatus;
}
