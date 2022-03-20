package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.RefreshToken;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.mock.JwtTokenMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.repository.RefreshTokenRepository;
import com.luv2code.travelchecker.service.impl.RefreshTokenServiceImpl;
import com.luv2code.travelchecker.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletResponse;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class RefreshTokenServiceImplTest {

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserDetailsService userDetailsService;

    private HttpServletResponse response;

    private RefreshToken refreshToken;

    private User firstUser;

    private UserDetails userDetails;

    private UUID uuid = UUID.fromString("b4725e9a-d7ad-4687-85f8-b47fe5ddd15b");

    @BeforeEach
    public void setup() {
        response = Mockito.mock(HttpServletResponse.class);

        firstUser = UserMock.createUser(uuid, "John", "Doe", "john.doe@gmail.com", "$2a$12$uhK.Qs/hRKLUzd1kwMec5uG8Gcht4dmNzMxCvji31.BMrEW4KiiR.");

        final Clock clock = Clock.fixed(
                Instant.parse("2050-01-12T10:05:22.635Z"),
                ZoneId.of("Europe/Zagreb")
        );
        refreshToken = new RefreshToken(firstUser, uuid, LocalDateTime.now(clock));

        userDetails = new org.springframework.security.core.userdetails.User(firstUser.getEmail(), firstUser.getPassword(), JwtTokenMock.userAuthorities);

        final String resetToken = JwtUtil.generateResetPasswordToken(firstUser.getEmail(), SecurityConstants.SECRET);

        try (MockedStatic<JwtUtil> mockedStatic = Mockito.mockStatic(JwtUtil.class)) {
            mockedStatic.when(() -> JwtUtil.generateResetPasswordToken(firstUser.getEmail(), SecurityConstants.SECRET)).thenReturn(resetToken);
        }

        BDDMockito.given(refreshTokenRepository.findByToken(firstUser.getId())).willReturn(Optional.ofNullable(refreshToken));
        BDDMockito.given(userDetailsService.loadUserByUsername(firstUser.getEmail())).willReturn(userDetails);
    }

    @Test
    public void should_Find_User_By_Token() {
        refreshTokenService.findByToken(uuid, response);
    }
}
