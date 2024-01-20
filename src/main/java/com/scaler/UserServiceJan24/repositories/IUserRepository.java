package com.scaler.UserServiceJan24.repositories;

import com.scaler.UserServiceJan24.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    Optional<User> findById(Long id);
    Optional<User> findByName(String name);

    List<User> findAll();
    User save(User user);

}
