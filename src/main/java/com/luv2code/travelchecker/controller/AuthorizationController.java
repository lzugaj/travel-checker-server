package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.mapper.UserMapper;
import com.luv2code.travelchecker.service.UserService;
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
@RequestMapping("/authorization")
public class AuthorizationController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public AuthorizationController(final UserService userService,
                                   final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<?> authorize(@Valid @RequestBody final UserPostDto userPostDto) {
        final User mappedUser = userMapper.dtoToEntity(userPostDto);
        LOGGER.info("Successfully mapped UserPostDto to User.");

        final User user = userService.save(mappedUser);
        LOGGER.info("Successfully finished authorization process for User with id: ´{}´.", user.getId());
        return new ResponseEntity<>(userMapper.entityToDto(user), HttpStatus.CREATED);
    }
}
