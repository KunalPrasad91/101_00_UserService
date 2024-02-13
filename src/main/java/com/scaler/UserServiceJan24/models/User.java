package com.scaler.UserServiceJan24.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    private String hashedPassword;
    private String phonenumber;
    private String address;
    private Boolean isEmailVerified;
    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles;
}
