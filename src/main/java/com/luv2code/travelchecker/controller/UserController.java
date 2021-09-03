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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(final UserService userService,
                          final AuthenticationService authenticationService,
                          final ModelMapper modelMapper) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final User searchedUser = userService.findById(id);
        LOGGER.info("Successfully founded User with id: ´{}´.", searchedUser.getId());
        return new ResponseEntity<>(modelMapper.map(searchedUser, UserGetDto.class), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable final String email) {
        final User searchedUser = userService.findByEmail(email);
        LOGGER.info("Successfully founded User with email: ´{}´.", searchedUser.getEmail());
        return new ResponseEntity<>(modelMapper.map(searchedUser, UserGetDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<User> users = userService.findAll();
        LOGGER.info("Successfully founded ´{}´ Users.", users.size());
        return new ResponseEntity<>(mapList(users), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody final UserPutDto userPutDto) {
        final String currentlyAuthenticatedUser = authenticationService.getAuthenticatedUser();
        LOGGER.info("Currently authenticated User with email: ´{}´.", currentlyAuthenticatedUser);

        final User mappedUser = modelMapper.map(userPutDto, User.class);
        LOGGER.info("Successfully mapped UserPutDto to User.");

        final User updatedUser = userService.update(currentlyAuthenticatedUser, mappedUser);
        LOGGER.info("Successfully finished updating process for User with id: ´{}´.", updatedUser.getId());
        return new ResponseEntity<>(modelMapper.map(updatedUser, UserGetDto.class), HttpStatus.OK);
    }

    private List<UserGetDto> mapList(final List<User> users) {
        return users
                .stream()
                .map(element -> modelMapper.map(element, UserGetDto.class))
                .collect(Collectors.toList());
    }
}
