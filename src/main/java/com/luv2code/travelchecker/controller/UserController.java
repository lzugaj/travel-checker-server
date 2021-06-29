package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody final UserPostDto userPostDto) {
        final UserGetDto userGetDto = userService.save(userPostDto);
        return new ResponseEntity<>(userGetDto, HttpStatus.CREATED);
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> save(@PathVariable final String username, @RequestBody final UserPutDto userPutDto) {
        final UserGetDto userGetDto = userService.update(username, userPutDto);
        return new ResponseEntity<>(userGetDto, HttpStatus.CREATED);
    }
}
