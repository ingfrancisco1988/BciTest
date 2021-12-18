package com.francisco.castanieda.BciTest.service.serviceImpl;

import com.francisco.castanieda.BciTest.exceptions.CustomException;
import com.francisco.castanieda.BciTest.exceptions.ValidationsException;
import com.francisco.castanieda.BciTest.model.DTO.ResponseUserDTO;
import com.francisco.castanieda.BciTest.model.DTO.UserDTO;
import com.francisco.castanieda.BciTest.model.Entity.User;
import com.francisco.castanieda.BciTest.repository.UserRepository;
import com.francisco.castanieda.BciTest.security.JWTRepository;
import com.francisco.castanieda.BciTest.service.UserService;
import com.francisco.castanieda.BciTest.utils.Validations;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl<JwtTokenProvider> implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JWTRepository jwtRepository;
    private final MapperFacade orikaMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTRepository jwtRepository, MapperFacade orikaMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtRepository = jwtRepository;
        this.orikaMapper = orikaMapper;
    }

    @Override
    public Optional<User> findUserById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User createUser(User newUser) throws CustomException {
        String uuid = java.util.UUID.randomUUID().toString();
        newUser.setId(uuid);
        if (!Validations.emailValidation(newUser.getEmail())){
              throw new ValidationsException("No se puede validar el mail","No cumple",HttpStatus.NOT_ACCEPTABLE);}
        if(!Validations.paswordValidation(newUser.getPassword())){
            throw new ValidationsException("La Password No cumple con los Standares de Aceptacion","No cumple",HttpStatus.NOT_ACCEPTABLE);}
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        newUser.setIsAdmin(auth.isAuthenticated());
        return this.userRepository.save(newUser);
    }

    @Override
    public ResponseUserDTO signin (final UserDTO userRequest){
        ResponseUserDTO userResponse = new ResponseUserDTO();
        try {
           // AuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> optionalUser =  this.userRepository.findUserByEmailAndPassword(userRequest.getEmail(),userRequest.getPassword());
            User user = optionalUser.orElseThrow(()-> new ValidationsException("Invalid username/password supplied","No cumple",HttpStatus.NOT_ACCEPTABLE) );
             jwtRepository.create(userRequest.getEmail(), user.getIsAdmin());

             return orikaMapper.map(user,ResponseUserDTO.class);

        } catch (AuthenticationException e) {
            throw new ValidationsException("Invalid username/password supplied","No cumple",HttpStatus.NOT_ACCEPTABLE);
        }
    }
}