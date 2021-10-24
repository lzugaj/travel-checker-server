package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.ResetPasswordToken;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.ForgetPasswordDto;
import com.luv2code.travelchecker.repository.PasswordResetTokenRepository;
import com.luv2code.travelchecker.service.ForgetPasswordService;
import com.luv2code.travelchecker.service.MailService;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordServiceImpl.class);

    private final UserService userService;

    private final MailService mailService;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public ForgetPasswordServiceImpl(final UserService userService,
                                     final MailService mailService,
                                     final PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userService = userService;
        this.mailService = mailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public void requestPasswordReset(final ForgetPasswordDto forgetPasswordDto) {
        final User searchedUser = userService.findByEmail(forgetPasswordDto.getEmail());
        LOGGER.info("Successfully founded User with id: {}.", searchedUser.getId());

        final String resetToken = JwtUtil.generateResetPasswordToken(searchedUser.getEmail());
        LOGGER.info("Successfully generated password reset token for User with id: {}.", searchedUser.getId());

        final ResetPasswordToken resetPasswordToken = buildPasswordResetToken(resetToken, searchedUser);
        passwordResetTokenRepository.save(resetPasswordToken);
        LOGGER.info("Successfully created new password reset token for User with id: {}.", searchedUser.getId());

        mailService.sendPasswordResetRequest(
                searchedUser.getFirstName(),
                searchedUser.getEmail(),
                resetToken);
    }

    private ResetPasswordToken buildPasswordResetToken(final String resetToken, final User user) {
        LOGGER.info("Building new password reset token for User with id: {}.", user.getId());
        return ResetPasswordToken.builder()
                .token(resetToken)
                .user(user)
                .build();
    }
}
