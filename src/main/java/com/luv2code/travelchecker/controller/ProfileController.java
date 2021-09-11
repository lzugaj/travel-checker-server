package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.service.AuthenticationService;
import com.luv2code.travelchecker.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final ModelMapper modelMapper;

    @Autowired
    public ProfileController(final UserService userService,
                             final AuthenticationService authenticationService,
                             final ModelMapper modelMapper) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@Valid @RequestBody final UserPutDto userPutDto) {
        final String email = authenticationService.getAuthenticatedEmail();
        LOGGER.info("Successfully founded currently logged in User with email: ´{}´.", email);

        final User mappedUser = modelMapper.map(userPutDto, User.class);
        LOGGER.info("Successfully mapped UserPutDto to User.");

        final User updatedUser = userService.update(email, mappedUser);
        LOGGER.info("Successfully finished updating process for User with id: ´{}´.", updatedUser.getId());
        return new ResponseEntity<>(modelMapper.map(updatedUser, UserGetDto.class), HttpStatus.OK);
    }
}
