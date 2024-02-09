package com.scaler.UserServiceJan24.repositories;

import com.scaler.UserServiceJan24.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Long> {

    Token save(Token token);
    Optional<Token> findByValueAndDeletedEquals(String token, Boolean deleted);

}
