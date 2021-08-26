package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
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

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(final UserService userService,
                          final ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final User searchedUser = userService.findById(id);
        LOGGER.info("Successfully founded User with id: ´{}´.", searchedUser.getId());
        return new ResponseEntity<>(modelMapper.map(searchedUser, UserGetDto.class), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable final String username) {
        final User searchedUser = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", searchedUser.getUsername());
        return new ResponseEntity<>(modelMapper.map(searchedUser, UserGetDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<User> users = userService.findAll();
        LOGGER.info("Successfully founded ´{}´ Users.", users.size());
        return new ResponseEntity<>(mapList(users, UserGetDto.class), HttpStatus.OK);
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> update(@PathVariable final String username, @RequestBody final UserPutDto userPutDto) {
        final User mappedUser = modelMapper.map(userPutDto, User.class);
        LOGGER.info("Successfully mapped UserPutDto to User.");

        final User updatedUser = userService.update(username, mappedUser);
        LOGGER.info("Successfully finished updating process for User with id: ´{}´.", updatedUser.getId());
        return new ResponseEntity<>(modelMapper.map(updatedUser, UserGetDto.class), HttpStatus.OK);
    }

    public List<UserGetDto> mapList(final List<User> source, final Class<UserGetDto> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
