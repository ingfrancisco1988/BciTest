package com.francisco.castanieda.BciTest.service;

import com.francisco.castanieda.BciTest.exceptions.CustomException;
import com.francisco.castanieda.BciTest.model.DTO.ResponseUserDTO;
import com.francisco.castanieda.BciTest.model.DTO.UserDTO;
import com.francisco.castanieda.BciTest.model.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService  {
    Optional<User> findUserById(String id);
    List<User> findAllUsers();
    User createUser(User newUser)throws CustomException;
    ResponseUserDTO signin (UserDTO user);
}
