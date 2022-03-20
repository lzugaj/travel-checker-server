package com.luv2code.travelchecker.mock;

import com.luv2code.travelchecker.dto.password.ResetPasswordDto;

public class ResetPasswordMock {

    public static ResetPasswordDto resetPasswordDto(final String newPassword,
                                                    final String confirmedNewPassword) {
        return ResetPasswordDto.builder()
                .newPassword(newPassword)
                .confirmedNewPassword(confirmedNewPassword)
                .build();
    }
}
