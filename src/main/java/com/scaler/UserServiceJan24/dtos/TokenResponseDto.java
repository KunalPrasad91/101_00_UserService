package com.scaler.UserServiceJan24.dtos;

import com.scaler.UserServiceJan24.enums.ResponseStatus;
import com.scaler.UserServiceJan24.models.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class TokenResponseDto {
    private String value;
    private Date expirydate;
    private UserResponseDto userResponseDto;
    private String message;
    private ResponseStatus responseStatus;
}
