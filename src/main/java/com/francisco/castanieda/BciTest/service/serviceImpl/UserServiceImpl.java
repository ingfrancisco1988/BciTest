package com.francisco.castanieda.BciTest.service.serviceImpl;

import com.francisco.castanieda.BciTest.exceptions.CustomException;
import com.francisco.castanieda.BciTest.exceptions.ValidationsException;
import com.francisco.castanieda.BciTest.model.dto.ResponseUserDTO;
import com.francisco.castanieda.BciTest.model.dto.UserDTO;
import com.francisco.castanieda.BciTest.model.entity.User;
import com.francisco.castanieda.BciTest.repository.UserRepository;
import com.francisco.castanieda.BciTest.security.JWTRepository;
import com.francisco.castanieda.BciTest.service.UserService;
import com.francisco.castanieda.BciTest.utils.Validations;
import ma.glasnost.orika.MapperFacade;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

import static com.francisco.castanieda.BciTest.model.constants.CustomConstants.SEED_ENCRYPTION;
import static com.francisco.castanieda.BciTest.security.SecurityConstants.TOKEN_PREFIX;
import static com.francisco.castanieda.BciTest.utils.JasyptUtil.decyptPwd;
import static com.francisco.castanieda.BciTest.utils.JasyptUtil.encyptPwd;

@Service
public class UserServiceImpl implements UserService {

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
    public User createUser(User newUser) throws CustomException {
        String uuid = java.util.UUID.randomUUID().toString();
        newUser.setId(uuid);
        String jwt =this.jwtRepository.create(newUser.getEmail());

        if (!Validations.emailValidation(newUser.getEmail())){
              throw new ValidationsException("No se puede validar el mail","No cumple",HttpStatus.NOT_ACCEPTABLE);}
        if(!Validations.paswordValidation(newUser.getPassword())){
            throw new ValidationsException("La Password No cumple con los Standares de Aceptacion","No cumple",HttpStatus.NOT_ACCEPTABLE);}
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        newUser.setPassword(encyptPwd(SEED_ENCRYPTION,newUser.getPassword()));
        newUser.setIsActive(true);
        newUser.setToken(TOKEN_PREFIX+jwt);
        newUser.setIsAdmin(auth.isAuthenticated());
        newUser.setCreated(new Timestamp(System.currentTimeMillis()));
        User optionalUser =  this.userRepository.findUserByEmailAndIsActive(newUser.getEmail(),newUser.getIsActive()).orElse(null);
         if (Objects.isNull(optionalUser)){
             return this.userRepository.save(newUser);
         }else{
             throw new ValidationsException("This Email exist in the Data Base","Error",HttpStatus.NOT_ACCEPTABLE);
         }

    }

    @Override
    public ResponseUserDTO sigIn (final UserDTO userRequest){
        try {
            Optional<User> optionalUser =  this.userRepository.findUserByEmailAndIsActive(userRequest.getEmail(),true);
            User userSearch = optionalUser.orElseThrow(()-> new ValidationsException("Error to Find the User","No cumple",HttpStatus.NOT_ACCEPTABLE) );
           String decrypt = decyptPwd(SEED_ENCRYPTION,userSearch.getPassword());
            if ((userRequest.getPassword()).equals(decrypt)) {
               userSearch.setLastLogin(new Timestamp(System.currentTimeMillis()));
               userSearch.setToken(jwtRepository.create(userRequest.getEmail()));
               User userResponse = this.userRepository.save(userSearch);
               return orikaMapper.map(userResponse, ResponseUserDTO.class);
           }else{
               throw new ValidationsException("Password is Inscorrect","No cumple",HttpStatus.NOT_ACCEPTABLE);
           }

        } catch (AuthenticationException e) {
            throw new ValidationsException("Invalid username/password supplied","No cumple",HttpStatus.NOT_ACCEPTABLE);
        }
    }
}