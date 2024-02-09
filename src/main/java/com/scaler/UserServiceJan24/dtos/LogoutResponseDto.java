package com.scaler.UserServiceJan24.dtos;

import com.scaler.UserServiceJan24.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutResponseDto {
    private String message;
    private ResponseStatus responseStatus;
}
