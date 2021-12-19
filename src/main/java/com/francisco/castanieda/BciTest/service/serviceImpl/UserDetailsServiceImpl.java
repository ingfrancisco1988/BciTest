package com.francisco.castanieda.BciTest.service.serviceImpl;

import com.francisco.castanieda.BciTest.exceptions.ValidationsException;
import com.francisco.castanieda.BciTest.model.entity.User;
import com.francisco.castanieda.BciTest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findUserByEmail(email) ;

        if (user == null) {
            throw new UsernameNotFoundException("User '" + email + "' not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .authorities("ADMIN")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}