package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.service.AuthenticationService;
import com.luv2code.travelchecker.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticatedUserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticatedUserController(final UserService userService,
                                       final AuthenticationService authenticationService,
                                       final ModelMapper modelMapper) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyAuthDetails() {
        final String email = authenticationService.getAuthenticatedEmail();
        LOGGER.debug("Founded currently logged in User. [email={}]", email);

        final User searchedUser = userService.findByEmail(email);
        LOGGER.info("Founded searched User. [email={}]", searchedUser.getEmail());
        return new ResponseEntity<>(modelMapper.map(searchedUser, UserGetDto.class), HttpStatus.OK);
    }
}
