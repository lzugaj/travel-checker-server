package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.User;

import java.util.List;

public interface UserService {

    User save(final User user);

    User findById(final Long id);

    User findByUsername(final String username);

    List<User> findAll();

    User update(final String username, final User user);

}
