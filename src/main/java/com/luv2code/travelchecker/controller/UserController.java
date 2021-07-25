package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.mapper.UserMapper;
import com.luv2code.travelchecker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserController(final UserService userService,
                          final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final User searchedUser = userService.findById(id);
        LOGGER.info("Successfully founded User with username: ´{}´.", searchedUser.getUsername());
        return new ResponseEntity<>(userMapper.entityToDto(searchedUser), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable final String username) {
        final User searchedUser = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", searchedUser.getUsername());
        return new ResponseEntity<>(userMapper.entityToDto(searchedUser), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<User> users = userService.findAll();
        LOGGER.info("Successfully founded ´{}´ Users.", users.size());
        return new ResponseEntity<>(userMapper.entitiesToDto(users), HttpStatus.OK);
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> update(@PathVariable final String username, @RequestBody final UserPutDto userPutDto) {
        final User searchedUser = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", username);

        final User updatedUser = userService.update(searchedUser, userPutDto);
        LOGGER.info("Successfully updated User with username: ´{}´.", updatedUser.getUsername());
        return new ResponseEntity<>(userMapper.entityToDto(updatedUser), HttpStatus.OK);
    }
}
