package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.exception.PasswordNotConfirmedRightException;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.RoleService;
import com.luv2code.travelchecker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractEntityServiceImpl<User, UserRepository> implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final RoleService roleService,
                           @Lazy final PasswordEncoder passwordEncoder) {
        super(userRepository, User.class);
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(final User user) {
        if (!user.getPassword().equals(user.getConfirmationPassword())) {
            LOGGER.error("Password is not repeated correctly for User with email: ´{}´.", user.getEmail());
            throw new PasswordNotConfirmedRightException("User", "password", user.getPassword());
        }

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

        user.addRole(findUserRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
    }

    private Role findUserRole() {
        final Role userRole = roleService.findByRoleType(RoleType.USER);
        LOGGER.info("Successfully founded Role with name: ´{}´.", userRole.getName().name());
        return userRole;
    }

    private boolean isUsernameAlreadyTaken(final String email) {
        return findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    private boolean isEmailAlreadyTaken(final String email) {
        return findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public User findById(final Long id) {
        return super.findById(id);
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
        return super.findAll();
    }

    @Override
    public User update(final String username, final User user) {
        if (checkIfUsernameIsNotAlreadyTaken(username, user)) {
            return super.save(user);
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
