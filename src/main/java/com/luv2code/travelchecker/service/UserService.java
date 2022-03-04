package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User save(final User user);

    User findById(final UUID id);

    User findByEmail(final String email);

    List<User> findAll();

    User update(final String username, final User user);

}
