package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.constants.SecurityConstants;
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
        LOGGER.info("Begin process of sending password reset request.");

        final User searchedUser = userService.findByEmail(forgetPasswordDto.getEmail());
        LOGGER.debug("Find searched User. [id={}]", searchedUser.getId());

        // TODO: SecurityConstants.SECRET
        final String resetToken = JwtUtil.generateResetPasswordToken(searchedUser.getEmail(), SecurityConstants.SECRET);
        LOGGER.debug("Generate password reset token for User. [id={}]", searchedUser.getId());

        final ResetPasswordToken resetPasswordToken = buildPasswordResetToken(resetToken, searchedUser);
        passwordResetTokenRepository.save(resetPasswordToken);
        LOGGER.debug("Create password reset token for User. [id={}]", searchedUser.getId());

        mailService.sendPasswordResetRequest(
                searchedUser.getFirstName(),
                searchedUser.getEmail(),
                resetToken);
    }

    // TODO: Model mapper?
    private ResetPasswordToken buildPasswordResetToken(final String resetToken, final User user) {
        return ResetPasswordToken.builder()
                .token(resetToken)
                .user(user)
                .build();
    }
}
