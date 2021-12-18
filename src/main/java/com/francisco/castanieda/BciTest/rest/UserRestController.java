package com.francisco.castanieda.BciTest.rest;

import com.francisco.castanieda.BciTest.exceptions.CustomException;
import com.francisco.castanieda.BciTest.exceptions.UserNotFoundException;
import com.francisco.castanieda.BciTest.model.DTO.ResponseUserDTO;
import com.francisco.castanieda.BciTest.model.Entity.User;
import com.francisco.castanieda.BciTest.model.DTO.UserDTO;
import com.francisco.castanieda.BciTest.security.JWTRepository;
import com.francisco.castanieda.BciTest.service.UserService;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.francisco.castanieda.BciTest.security.SecurityConstants.HEADER_STRING;
import static com.francisco.castanieda.BciTest.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserRestController {


    @Autowired
    private UserService userService;

    @Autowired
    private JWTRepository jwtRepository;


    @PostMapping("/sign-up")
    public ResponseEntity<Object> createUser( @RequestBody UserDTO user) throws CustomException {
        String jwt =this.jwtRepository.create(user.getEmail(),true);

        User newUser =  new User();
        //newUser.setId(jwt);
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setPhones(user.getPhones());
        this.userService.createUser(newUser);
        return new ResponseEntity< >("Usuario Creado Correctamente: "+ TOKEN_PREFIX+jwt,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseUserDTO login(@RequestBody UserDTO user) {
        return userService.signin(user);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");
        JWTRepository.getInstance().removeToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
