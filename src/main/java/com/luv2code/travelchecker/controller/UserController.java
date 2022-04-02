package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
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
    public ResponseEntity<UserGetDto> findById(@PathVariable final UUID id) {
        final User searchedUser = userService.findById(id);
        LOGGER.info("Found searched User. [id={}]", searchedUser.getId());
        return new ResponseEntity<>(modelMapper.map(searchedUser, UserGetDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserGetDto>> findAll() {
        final List<User> users = userService.findAll();
        LOGGER.info("Found all searched Users. [size={}]", users.size());
        return new ResponseEntity<>(mapList(users), HttpStatus.OK);
    }

    private List<UserGetDto> mapList(final List<User> users) {
        return users
                .stream()
                .map(element -> modelMapper.map(element, UserGetDto.class))
                .collect(Collectors.toList());
    }
}
