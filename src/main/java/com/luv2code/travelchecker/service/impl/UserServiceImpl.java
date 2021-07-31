package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(final User user) {
        if (isUsernameAlreadyTaken(user.getUsername())) {
            LOGGER.error("User already exists with username: ´{}´.", user.getUsername());
            throw new EntityAlreadyExistsException(
                    "User", "username", user.getUsername());
        }

        if (user.getEmail() != null && isEmailAlreadyTaken(user.getEmail())) {
            LOGGER.error("User already exists with email: ´{}´.", user.getUsername());
            throw new EntityAlreadyExistsException(
                    "User", "email", user.getEmail());
        }

        final User newUser = userRepository.save(user);
        LOGGER.info("Successfully created User with id: ´{}´.", user.getId());
        return newUser;
    }

    private boolean isUsernameAlreadyTaken(final String username) {
        return findAll().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    private boolean isEmailAlreadyTaken(final String email) {
        return findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public User findById(final Long id) {
        LOGGER.info("Searching User with id: ´{}´.", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                        LOGGER.error("Cannot find User with id: ´{}´.", id);
                        return new EntityNotFoundException("User", "id", String.valueOf(id));
                });
    }

    @Override
    public User findByUsername(final String username) {
        LOGGER.info("Searching User with username: ´{}´.", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find User with username: ´{}´.", username);
                    return new EntityNotFoundException("User", "username", username);
                });
    }

    @Override
    public List<User> findAll() {
        LOGGER.info("Searching all Users.");
        return userRepository.findAll();
    }

    @Override
    public User update(final String username, final User user) {
        if (checkIfUsernameIsNotAlreadyTaken(username, user)) {
            final User updatedUser = userRepository.save(user);
            LOGGER.info("Successfully updated User with id: ´{}´.", user.getId());
            return updatedUser;
        } else {
            LOGGER.error("User already exists with username: ´{}´.", user.getUsername());
            throw new EntityAlreadyExistsException(
                    "User", "username", user.getUsername());
        }
    }

    private boolean checkIfUsernameIsNotAlreadyTaken(final String username, final User currentUser) {
        if (!currentUser.getUsername().equals(username)) {
            final List<User> users = findAll();
            for (User user : users) {
                if (usernameIsNotAlreadyTaken(currentUser, user)) return false;
            }
        }

        return true;
    }

    private boolean usernameIsNotAlreadyTaken(final User currentUser, final User user) {
        boolean areUsernamesEquals = true;
        if (!user.equals(currentUser)) {
            areUsernamesEquals = user.getUsername().equals(currentUser.getUsername());
        }

        return areUsernamesEquals;
    }
}
