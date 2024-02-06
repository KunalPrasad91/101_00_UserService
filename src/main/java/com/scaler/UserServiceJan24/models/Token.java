package com.scaler.UserServiceJan24.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModel{
    private String value;
    private Date expirydate;
    // since multiple login
    @ManyToOne
    private User user;
}
