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

    private final PasswordEncoder passwordEncoder; // TODO Lazy?

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
        if (arePasswordsNotEquals(user.getPassword(), user.getConfirmationPassword())) {
            LOGGER.error("Password for User with email: ´{}´ is not confirmed correctly.", user.getEmail());
            throw new PasswordNotConfirmedRightException("Password for User with id: " + user.getId() + " is not confirmed right.");
        }

        if (isEmailAlreadyTaken(user.getEmail())) {
            LOGGER.error("User with email: ´{}´.", user.getEmail() + " already exists.");
            throw new EntityAlreadyExistsException("User with email: " + user.getEmail() + " already exists.");
        }

        user.addRole(findUserRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
    }

    private boolean arePasswordsNotEquals(final String password, final String confirmationPassword) {
        return !password.equals(confirmationPassword);
    }

    private boolean isEmailAlreadyTaken(final String email) {
        return userRepository.existsByEmail(email);
    }

    private Role findUserRole() {
        final Role userRole = roleService.findByRoleType(RoleType.USER);
        LOGGER.info("Successfully founded Role with name: ´{}´.", userRole.getName().name());
        return userRole;
    }

    @Override
    public User findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public User findByEmail(final String email) {
        LOGGER.info("Searching User with email: ´{}´.", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    LOGGER.error("User with email: " + email + " was not found.");
                    throw new EntityNotFoundException("User with email: " + email + " was not found.");
                });
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    // TODO: 2 metode => 1. Partial update User info
    //                   2. Update User email in separate method

    @Override
    public User update(final String email, final User user) {
        if (checkIfEmailIsNotAlreadyTaken(email, user)) {
            return super.save(user);
        } else {
            LOGGER.error("User with email: ´{}´ already exists.", user.getEmail());
            throw new EntityAlreadyExistsException("User with email: " + user.getEmail() + " already exists.");
        }
    }

    // TODO: lzugaj -> Refactor
    private boolean checkIfEmailIsNotAlreadyTaken(final String email, final User currentUser) {
        if (!currentUser.getEmail().equals(email)) {
            final List<User> users = findAll();
            for (User user : users) {
                if (emailIsNotAlreadyTaken(currentUser, user)) return false;
            }
        }

        return true;
    }

    // TODO: lzugaj -> Refactor
    private boolean emailIsNotAlreadyTaken(final User currentUser, final User user) {
        boolean areEmailsEquals = true;
        if (!user.equals(currentUser)) {
            areEmailsEquals = user.getEmail().equals(currentUser.getEmail());
        }

        return areEmailsEquals;
    }
}
