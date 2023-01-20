package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.ResetPasswordDto;
import com.luv2code.travelchecker.exception.PasswordNotConfirmedRightException;
import com.luv2code.travelchecker.mock.JwtTokenMock;
import com.luv2code.travelchecker.mock.ResetPasswordMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.service.impl.ResetPasswordServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootTest
public class ResetPasswordServiceImplTest {

    @InjectMocks
    private ResetPasswordServiceImpl resetPasswordService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void should_Reset_Password_When_Data_Is_Valid() {
        // ResetPasswordDto
        final ResetPasswordDto resetPasswordDto = ResetPasswordMock.resetPasswordDto("#Testanovich1234", "#Testanovich1234");

        // User
        final User user = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "john.doe@gmail.com", "$2a$12$FFeIqj0cyzYZAkHuHWIWyewkuYSX6YTK/6ygNzHfXSe1L2vc8VmIy");

        // Token
        final String resetPasswordToken = JwtTokenMock.generateResetPasswordToken(user.getEmail(), SecurityConstants.SECRET);

        BDDMockito.given(passwordEncoder.encode(resetPasswordDto.getNewPassword())).willReturn("$2a$12$uCLxVNxxwpIKmwEUqOxOqONK9lCudTQ9yD88sPlmE7kSTr2rVR62G");
        BDDMockito.given(userService.findByEmail(user.getEmail())).willReturn(user);

        resetPasswordService.resetPassword(resetPasswordToken, resetPasswordDto);

        Mockito.verify(userService).update(user.getEmail(), user);

        // Assertions.assertTrue(passwordEncoder.matches("#Password1234", user.getPassword()));
    }

    @Test
    public void should_Throw_Exception_When_Password_Is_Expired() {
        // ResetPasswordDto
        final ResetPasswordDto resetPasswordDto = ResetPasswordMock.resetPasswordDto("#Testanovich1234", "#Testanovich1234");

        // User
        final User user = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "john.doe@gmail.com", "$2a$12$FFeIqj0cyzYZAkHuHWIWyewkuYSX6YTK/6ygNzHfXSe1L2vc8VmIy");

        // Token
        final String expiredResetPasswordToken = JwtTokenMock.generateResetPasswordTokenThatIsExpired(user.getEmail(), SecurityConstants.SECRET);

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

        final Exception exception = Assertions.assertThrows(
                PasswordNotConfirmedRightException.class,
                () -> resetPasswordService.resetPassword(resetPasswordToken, resetPasswordDto)
        );

        final String expectedMessage = "Password is not confirmed right while resetting password.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
