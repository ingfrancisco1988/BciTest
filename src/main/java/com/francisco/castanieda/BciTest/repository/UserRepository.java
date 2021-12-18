package com.francisco.castanieda.BciTest.repository;

import com.francisco.castanieda.BciTest.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByEmailAndPassword(final String email,final String password);
}