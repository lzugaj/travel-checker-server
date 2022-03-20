package com.luv2code.travelchecker.mock;

import com.luv2code.travelchecker.domain.ResetPasswordToken;
import com.luv2code.travelchecker.domain.User;

import java.util.UUID;

public class ResetPasswordTokenMock {

    public static ResetPasswordToken createResetPasswordToken(final UUID id,
                                                              final String resetToken,
                                                              final User user) {
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setId(id);
        resetPasswordToken.setToken(resetToken);
        resetPasswordToken.setUser(user);

        return resetPasswordToken;
    }
}
