package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.dto.password.ForgetPasswordDto;

public interface ForgetPasswordService {

    void requestPasswordReset(final ForgetPasswordDto forgetPasswordDto);

}
