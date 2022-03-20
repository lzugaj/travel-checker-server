package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.ResetPasswordToken;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.ForgetPasswordDto;
import com.luv2code.travelchecker.mock.ForgetPasswordMock;
import com.luv2code.travelchecker.mock.ResetPasswordTokenMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.repository.PasswordResetTokenRepository;
import com.luv2code.travelchecker.service.impl.ForgetPasswordServiceImpl;
import com.luv2code.travelchecker.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class ForgetPasswordServiceImplTest {

    @InjectMocks
    private ForgetPasswordServiceImpl forgetPasswordService;

    @Mock
    private UserService userService;

    @Mock
    private MailService mailService;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private ForgetPasswordDto forgetPasswordDto;

    @BeforeEach
    public void setup() {
        // User
        final User user = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");

        // ResetPasswordToken
        final String resetToken = JwtUtil.generateResetPasswordToken(user.getEmail(), SecurityConstants.SECRET);
        final ResetPasswordToken resetPasswordToken = ResetPasswordTokenMock.createResetPasswordToken(UUID.randomUUID(), resetToken, user);

        // ForgetPasswordDto
        forgetPasswordDto = ForgetPasswordMock.forgetPasswordDto(user.getEmail());

        try (MockedStatic<JwtUtil> mockedStatic = Mockito.mockStatic(JwtUtil.class)) {
            mockedStatic.when(() -> JwtUtil.generateResetPasswordToken(user.getEmail(), SecurityConstants.SECRET)).thenReturn(resetToken);
        }

        BDDMockito.given(userService.findByEmail(user.getEmail())).willReturn(user);
        BDDMockito.given(passwordResetTokenRepository.save(resetPasswordToken)).willReturn(resetPasswordToken);
        BDDMockito.doNothing().when(mailService).sendPasswordResetRequest(user.getFirstName(), user.getEmail(), resetToken);
    }

    @Test
    public void should_Request_Password_Reset() {
        forgetPasswordService.requestPasswordReset(forgetPasswordDto);

        Mockito.verify(mailService).sendPasswordResetRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
