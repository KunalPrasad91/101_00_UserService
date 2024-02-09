package com.scaler.UserServiceJan24.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UserLogoutRequestDto {
    private String token;
}
