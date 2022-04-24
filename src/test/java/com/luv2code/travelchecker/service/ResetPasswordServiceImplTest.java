package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.ResetPasswordDto;
import com.luv2code.travelchecker.exception.PasswordNotConfirmedRightException;
import com.luv2code.travelchecker.mock.JwtTokenMock;
import com.luv2code.travelchecker.mock.ResetPasswordMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.service.impl.ResetPasswordServiceImpl;
import com.luv2code.travelchecker.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class ResetPasswordServiceImplTest {

    @InjectMocks
    private ResetPasswordServiceImpl resetPasswordService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final Long EXPIRATION = 1_500_000L; // 25 minutes

    @Test
    public void should_Reset_Password_When_Data_Is_Valid() {
        // ResetPasswordDto
        final ResetPasswordDto resetPasswordDto = ResetPasswordMock.resetPasswordDto("#Testanovich1234", "#Testanovich1234");

        // User
        final User user = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "john.doe@gmail.com", "$2a$12$FFeIqj0cyzYZAkHuHWIWyewkuYSX6YTK/6ygNzHfXSe1L2vc8VmIy\n");

        // Token
        final String resetPasswordToken = JwtTokenMock.generateResetPasswordToken(user.getEmail(), SecurityConstants.SECRET);

        try (MockedStatic<JwtUtil> mockedStatic = Mockito.mockStatic(JwtUtil.class)) {
            mockedStatic.when(() -> JwtUtil.extractUsername(resetPasswordToken, SecurityConstants.SECRET)).thenReturn("john.doe@gmail.com");
            mockedStatic.when(() -> JwtUtil.extractExpiration(resetPasswordToken, SecurityConstants.SECRET)).thenReturn(new Date(System.currentTimeMillis() + SecurityConstants.RESET_PASSWORD_EXPIRATION_TIME));
        }

        BDDMockito.given(passwordEncoder.encode(resetPasswordDto.getNewPassword())).willReturn("$2a$12$Vlh7WvrFsMCVJLT6DO6zsen9rvyHA9.9o/9yjKkaxMYWQZBuBt2dK");
        BDDMockito.given(userService.findByEmail(user.getEmail())).willReturn(user);

        resetPasswordService.resetPassword(resetPasswordToken, resetPasswordDto);

        Mockito.verify(userService).update(user.getEmail(), user);

        // TODO: Assertion
    }

    @Test
    public void should_Throw_Exception_When_Password_Is_Expired() {
        // ResetPasswordDto
        final ResetPasswordDto resetPasswordDto = ResetPasswordMock.resetPasswordDto("#Testanovich1234", "#Testanovich1234");

        // User
        final User user = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "john.doe@gmail.com", "$2a$12$FFeIqj0cyzYZAkHuHWIWyewkuYSX6YTK/6ygNzHfXSe1L2vc8VmIy");

        // Token
        final String expiredResetPasswordToken = JwtTokenMock.generateResetPasswordTokenThatIsExpired(user.getEmail(), SecurityConstants.SECRET);

        try (MockedStatic<JwtUtil> mockedStatic = Mockito.mockStatic(JwtUtil.class)) {
            mockedStatic.when(() -> JwtUtil.validateJwtToken(expiredResetPasswordToken, SecurityConstants.SECRET)).thenThrow(ExpiredJwtException.class);
        }

        final Exception exception = Assertions.assertThrows(
                ExpiredJwtException.class,
                () -> resetPasswordService.resetPassword(expiredResetPasswordToken, resetPasswordDto)
        );

        Assertions.assertEquals(exception.getClass(), ExpiredJwtException.class);
    }


    @Test
    public void should_Throw_Exception_When_Password_Are_Not_Equals() {
        // ResetPasswordDto
        final ResetPasswordDto resetPasswordDto = ResetPasswordMock.resetPasswordDto("#Testanovich1234", "#Testanovi124");

        // User
        final User user = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "john.doe@gmail.com", "$2a$12$FFeIqj0cyzYZAkHuHWIWyewkuYSX6YTK/6ygNzHfXSe1L2vc8VmIy\n");

        // Token
        final String resetPasswordToken = JwtTokenMock.generateResetPasswordToken(user.getEmail(), SecurityConstants.SECRET);

        try (MockedStatic<JwtUtil> mockedStatic = Mockito.mockStatic(JwtUtil.class)) {
            mockedStatic.when(() -> JwtUtil.extractExpiration(resetPasswordToken, SecurityConstants.SECRET)).thenReturn(new Date(System.currentTimeMillis() + EXPIRATION));
        }

        final Exception exception = Assertions.assertThrows(
                PasswordNotConfirmedRightException.class,
                () -> resetPasswordService.resetPassword(resetPasswordToken, resetPasswordDto)
        );

        final String expectedMessage = "Password is not confirmed right while resetting password.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
