package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;

import java.util.List;

public interface UserService {

    UserGetDto save(final UserPostDto userPostDto);

    UserGetDto findById(final Long id);

    UserGetDto findByUsername(final String username);

    List<UserGetDto> findAll();

    UserGetDto update(final String username, final UserPutDto newUser);

}
