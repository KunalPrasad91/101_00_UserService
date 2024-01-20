package com.scaler.UserServiceJan24.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String password;
    private String phonenumber;
    private String address;
}
