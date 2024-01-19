package com.scaler.UserServiceJan24.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.scaler.UserServiceJan24.models.User}
 */


@Getter
@Setter
public class UserDto  {
    private String name;
    private String message;
}