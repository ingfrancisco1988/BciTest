package com.francisco.castanieda.BciTest.security;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityUtilsTest extends  AbstractRestControllerTest{
    @Before("")
    public void setUp() {
        SecurityContextHolder.clearContext();
    }
    
    @Test
    public void getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        assertThat(username).contains("admin");
    }

    @Test
    public void getActualUserForUserWithoutToken() throws Exception {
        getMockMvc().perform(get("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}
