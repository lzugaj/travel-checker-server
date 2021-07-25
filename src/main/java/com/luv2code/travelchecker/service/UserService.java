package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.PasswordUpdateDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;

import java.util.List;

public interface UserService {

    User save(final UserPostDto userPostDto);

    User findById(final Long id);

    User findByUsername(final String username);

    List<User> findAll();

    User update(final User updatedUser, final UserPutDto newUser);

    User changePassword(final User updatedUser, final PasswordUpdateDto passwordUpdateDto);

}
