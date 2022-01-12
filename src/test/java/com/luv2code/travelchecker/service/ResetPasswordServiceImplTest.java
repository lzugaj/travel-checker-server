package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.ResetPasswordDto;
import com.luv2code.travelchecker.service.impl.ResetPasswordServiceImpl;
import com.luv2code.travelchecker.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class ResetPasswordServiceImplTest {

    @InjectMocks
    private ResetPasswordServiceImpl resetPasswordService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ResetPasswordDto resetPasswordDto;
    private User user;

    private static String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsenVnYWpAZ21haWwuY29tIiwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiZXhwIjoxNjM2OTE1MzQzLCJpYXQiOjE2MzY5MTUyMjN9.v_XsbJeqjpAVHn1XknjR34r3tUJgv_37qWbEYe1U4QJnLfeWA1yR_7gvO_lfdbCr87fGBbnH-lHWg_c_L7On_Q";

    @BeforeEach
    public void setup() {
        resetPasswordDto = new ResetPasswordDto("#test1234", "#test1234");

        user = UserUtil.createUser(1L, "Luka", "Zugaj", "lzugaj@gmail.com", "#Password1234");

        /*Mockito.when(JwtUtil.extractExpiration(TOKEN, SecurityConstants.SECRET)).thenReturn(new Date());
        Mockito.when(JwtUtil.extractUsername(TOKEN, SecurityConstants.SECRET)).thenReturn("lzugaj@gmail.com");*/
        Mockito.when(userService.findByEmail(user.getEmail())).thenReturn(user);
    }

    @Test
    public void should_Reset_Password() {

    }
}
