package com.francisco.castanieda.BciTest.service;

import com.francisco.castanieda.BciTest.exceptions.CustomException;
import com.francisco.castanieda.BciTest.model.dto.ResponseUserDTO;
import com.francisco.castanieda.BciTest.model.dto.UserDTO;
import com.francisco.castanieda.BciTest.model.entity.User;

public interface UserService  {
    User createUser(User newUser)throws CustomException;
    ResponseUserDTO sigIn (UserDTO user);
}
