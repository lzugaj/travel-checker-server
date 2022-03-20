package com.luv2code.travelchecker.mock;

import com.luv2code.travelchecker.dto.password.ForgetPasswordDto;

public class ForgetPasswordMock {

    public static ForgetPasswordDto forgetPasswordDto(final String email) {
        final ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
        forgetPasswordDto.setEmail(email);
        return forgetPasswordDto;
    }
}
