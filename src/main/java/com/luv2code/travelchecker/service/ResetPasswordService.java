package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.dto.password.ResetPasswordDto;

public interface ResetPasswordService {

    void resetPassword(final String token, final ResetPasswordDto resetPasswordDto);

}
