package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.dto.password.ResetPasswordDto;
import com.luv2code.travelchecker.service.ResetPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordController.class);

    private final ResetPasswordService resetPasswordService;

    @Autowired
    public ResetPasswordController(final ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping
    public ResponseEntity<Void> resetPassword(@RequestParam(required = false) final String token,
                                           @Valid @RequestBody final ResetPasswordDto resetPasswordDto) {
        resetPasswordService.resetPassword(token, resetPasswordDto);
        LOGGER.info("Finish process of sending password reset request.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
