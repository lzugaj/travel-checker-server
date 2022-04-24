package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.RefreshToken;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.exception.TokenRefreshException;
import com.luv2code.travelchecker.mock.JwtTokenMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.repository.RefreshTokenRepository;
import com.luv2code.travelchecker.service.impl.RefreshTokenServiceImpl;
import com.luv2code.travelchecker.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
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

    private final UUID uuid = UUID.fromString("b4725e9a-d7ad-4687-85f8-b47fe5ddd15b");

    @BeforeEach
    public void setup() {
        response = new MockHttpServletResponse();

        firstUser = UserMock.createUser(uuid, "John", "Doe", "john.doe@gmail.com", "$2a$12$uhK.Qs/hRKLUzd1kwMec5uG8Gcht4dmNzMxCvji31.BMrEW4KiiR.");

        final Clock clock = Clock.fixed(
                Instant.parse("2050-01-12T10:05:22.635Z"),
                ZoneId.of("Europe/Zagreb"));

        refreshToken = new RefreshToken(firstUser, uuid, LocalDateTime.now(clock));
        final UserDetails userDetails = new org.springframework.security.core.userdetails.User(firstUser.getEmail(), firstUser.getPassword(), JwtTokenMock.userAuthorities);

        final String resetToken = JwtUtil.generateResetPasswordToken(firstUser.getEmail(), SecurityConstants.SECRET);

        try (MockedStatic<JwtUtil> mockedStatic = Mockito.mockStatic(JwtUtil.class)) {
            mockedStatic.when(() -> JwtUtil.generateResetPasswordToken(firstUser.getEmail(), SecurityConstants.SECRET)).thenReturn(resetToken);
        }

        BDDMockito.given(refreshTokenRepository.findByToken(firstUser.getId())).willReturn(Optional.ofNullable(refreshToken));
        BDDMockito.given(userDetailsService.loadUserByUsername(firstUser.getEmail())).willReturn(userDetails);
        BDDMockito.given(refreshTokenRepository.save(refreshToken)).willReturn(refreshToken);
    }

    @Test
    public void should_Find_User_By_Token() {
        refreshTokenService.findByToken(uuid, response);

        // Assertions.assertEquals(String.format("Bearer %s", resetToken), String.valueOf(response.getHeader("access-token")));
        Assertions.assertEquals(String.valueOf(uuid), response.getHeader("refresh-token"));
    }

    @Test
    public void should_Throw_Exception_When_User_Is_Not_Found() {
        final Exception exception = Assertions.assertThrows(
                TokenRefreshException.class,
                () -> refreshTokenService.findByToken(UUID.fromString("b4725e9a-d7ad-4687-85f8-b47fe5ddd15a"), response)
        );

        final String expectedMessage = "Refresh token is not founded in database.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Create_Refresh_Token() {
        final RefreshToken refreshToken = refreshTokenService.create(firstUser);

        Assertions.assertEquals("john.doe@gmail.com", refreshToken.getUser().getEmail());
        Assertions.assertEquals(uuid, refreshToken.getToken());
    }

    @Test
    public void should_Return_Refresh_Token_When_It_Is_Not_Expired() {
        final RefreshToken verifiedRefreshToken = refreshTokenService.verifyExpiration(refreshToken);

        Assertions.assertEquals("john.doe@gmail.com", verifiedRefreshToken.getUser().getEmail());
        Assertions.assertEquals(uuid, verifiedRefreshToken.getToken());
    }

    @Test
    public void should_Throw_Exception_When_Refresh_Token_Is_Expired() {
        final Clock clock = Clock.fixed(
                Instant.parse("2018-01-12T10:05:22.635Z"),
                ZoneId.of("Europe/Zagreb"));

        final RefreshToken expiredRefreshToken = new RefreshToken(firstUser, uuid, LocalDateTime.now(clock));

        final Exception exception = Assertions.assertThrows(
                TokenRefreshException.class,
                () -> refreshTokenService.verifyExpiration(expiredRefreshToken)
        );

        final String expectedMessage = String.format("Refresh token has expired for User. [id=%s]", expiredRefreshToken.getUser().getId());
        final String actualMessage = exception.getMessage();

        Mockito.verify(refreshTokenRepository, Mockito.times(1)).delete(expiredRefreshToken);

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
