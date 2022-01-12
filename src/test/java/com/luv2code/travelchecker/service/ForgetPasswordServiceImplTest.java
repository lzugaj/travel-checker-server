package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.ResetPasswordToken;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.ForgetPasswordDto;
import com.luv2code.travelchecker.repository.PasswordResetTokenRepository;
import com.luv2code.travelchecker.service.impl.ForgetPasswordServiceImpl;
import com.luv2code.travelchecker.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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
        final User user = UserUtil.createUser(1L, "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");

        // final String resetToken = JwtTokenUtil.createResetPasswordToken(user.getEmail());

        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setId(1L);
        // resetPasswordToken.setToken(resetToken);
        resetPasswordToken.setUser(user);

        forgetPasswordDto = new ForgetPasswordDto();
        forgetPasswordDto.setEmail(user.getEmail());

        Mockito.when(userService.findByEmail(user.getEmail())).thenReturn(user);
        Mockito.when(passwordResetTokenRepository.save(resetPasswordToken)).thenReturn(resetPasswordToken);
    }

    @Test
    public void should_Request_Password_Reset() {
        forgetPasswordService.requestPasswordReset(forgetPasswordDto);
    }
}
