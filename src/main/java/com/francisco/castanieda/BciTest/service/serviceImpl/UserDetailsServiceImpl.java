package com.francisco.castanieda.BciTest.service.serviceImpl;

import com.francisco.castanieda.BciTest.model.Entity.User;
import com.francisco.castanieda.BciTest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getIsAdmin() ?
                Collections.singletonList(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ADMIN";
                    }
                }) : emptyList());
    }

}