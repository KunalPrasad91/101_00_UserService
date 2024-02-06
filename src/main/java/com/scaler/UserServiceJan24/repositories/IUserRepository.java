package com.scaler.UserServiceJan24.repositories;

import com.scaler.UserServiceJan24.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    List<User> findAll();
    User save(User user);

    void deleteById(Long id);

    /*
    Reason for @Transactional
    https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
     */
    @Transactional
    void deleteByName(String name);

}
