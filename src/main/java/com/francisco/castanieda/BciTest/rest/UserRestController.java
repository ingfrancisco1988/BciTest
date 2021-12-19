package com.francisco.castanieda.BciTest.rest;

import com.francisco.castanieda.BciTest.exceptions.CustomException;
import com.francisco.castanieda.BciTest.model.dto.ResponseUserDTO;
import com.francisco.castanieda.BciTest.model.entity.User;
import com.francisco.castanieda.BciTest.model.dto.UserDTO;
import com.francisco.castanieda.BciTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserRestController {


    @Autowired
    private UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<Object> createUser( @RequestBody UserDTO user) throws CustomException {

        User newUser =  new User();
        //newUser.setId(jwt);

        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setPhones(user.getPhones());
        this.userService.createUser(newUser);
        return new ResponseEntity< >("Usuario Creado Correctamente: "+newUser.getToken(),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseUserDTO login(@RequestBody UserDTO user) {
        return userService.sigIn(user);
    }

}
