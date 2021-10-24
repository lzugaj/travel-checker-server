package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.dto.password.ForgetPasswordDto;
import com.luv2code.travelchecker.service.ForgetPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/forget-password")
public class ForgetPasswordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordController.class);

    private final ForgetPasswordService forgetPasswordService;

    @Autowired
    public ForgetPasswordController(final ForgetPasswordService forgetPasswordService) {
        this.forgetPasswordService = forgetPasswordService;
    }

    @PostMapping
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody final ForgetPasswordDto forgetPasswordDto) {
        forgetPasswordService.requestPasswordReset(forgetPasswordDto);
        LOGGER.info("Successfully requested password reset.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
