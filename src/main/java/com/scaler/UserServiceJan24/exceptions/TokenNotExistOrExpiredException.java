package com.scaler.UserServiceJan24.exceptions;

import com.scaler.UserServiceJan24.models.Token;

public class TokenNotExistOrExpiredException extends Exception{

    public TokenNotExistOrExpiredException(String message)
    {
        super(message);
    }
}
