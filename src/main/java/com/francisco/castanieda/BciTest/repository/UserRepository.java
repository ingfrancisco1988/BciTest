package com.francisco.castanieda.BciTest.repository;

import com.francisco.castanieda.BciTest.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
     User findUserByEmail(String email);
    Optional<User>findUserByEmailAndIsActive(String email,boolean active);
}