package com.scaler.UserServiceJan24.dtos;

import com.scaler.UserServiceJan24.models.Role;
import com.scaler.UserServiceJan24.models.User;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.scaler.UserServiceJan24.models.User}
 */


@Getter
@Setter
public class UserDto  {
    private String name;
    private String email;
    private String phonenumber;
    private String address;
    private Boolean isEmailVerified;
    private List<Role> roles;

    public  static UserDto fromUser(User user)
    {
        if(user == null)
            return null;

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setRoles(user.getRoles());
        userDto.setIsEmailVerified(user.getIsEmailVerified());

        return userDto;
    }
}