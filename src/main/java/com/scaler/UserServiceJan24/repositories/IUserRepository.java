package com.scaler.UserServiceJan24.repositories;

import com.scaler.UserServiceJan24.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    User save(User user);
}
