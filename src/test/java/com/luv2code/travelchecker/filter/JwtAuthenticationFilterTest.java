package com.luv2code.travelchecker.filter;

import com.luv2code.travelchecker.configuration.JwtProperties;
import com.luv2code.travelchecker.service.RefreshTokenService;
import com.luv2code.travelchecker.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private UserService userService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtProperties jwtProperties;

}
