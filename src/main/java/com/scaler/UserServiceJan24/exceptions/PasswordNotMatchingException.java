package com.scaler.UserServiceJan24.exceptions;

public class PasswordNotMatchingException extends Exception{
    public PasswordNotMatchingException(String message)
    {
        super(message);
    }
}
