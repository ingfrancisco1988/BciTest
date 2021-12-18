package com.francisco.castanieda.BciTest.security;

import com.auth0.jwt.JWT;
import com.francisco.castanieda.BciTest.service.serviceImpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.francisco.castanieda.BciTest.security.SecurityConstants.EXPIRATION_TIME;
import static com.francisco.castanieda.BciTest.security.SecurityConstants.SECRET;

@Component
public class JWTRepository {
    public static final JWTRepository instance = new JWTRepository();
    private List<String> tokens = new ArrayList<>();

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private JWTRepository() {
    }

    public static JWTRepository getInstance() {
        return instance;
    }

    public void addToken(String token) {
        this.tokens.add(token);
    }

    public void removeToken(String token) {
        this.tokens.remove(token);
    }

    public boolean hasToken(String token) {
        return this.tokens.contains(token);
    }


    public String create(String username, boolean isAdmin) {
        String token = JWT.create()
                .withSubject(username)
                .withClaim("admin", isAdmin)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        addToken(token);
        return token;
    }

    public String decode(String token) {
        return JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(decode(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}