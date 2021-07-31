package com.luv2code.travelchecker.utils;

import com.luv2code.travelchecker.dto.password.PasswordUpdateDto;

public class PasswordUpdateUtil {

    public static PasswordUpdateDto createPasswordUpdateDto(final String oldPassword, final String newPassword, final String confirmedNewPassword) {
        final PasswordUpdateDto passwordUpdateDto = new PasswordUpdateDto();
        passwordUpdateDto.setOldPassword(oldPassword);
        passwordUpdateDto.setNewPassword(newPassword);
        passwordUpdateDto.setConfirmedNewPassword(confirmedNewPassword);
        return passwordUpdateDto;
    }
}
