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
import java.util.UUID;

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
        LOGGER.info("Begin process of saving new User.");
        if (arePasswordsNotEquals(user.getPassword(), user.getConfirmationPassword())) {
            LOGGER.error("Password is not confirmed correctly for User. [id={}]", user.getId());
            throw new PasswordNotConfirmedRightException(
                    String.format("Password is not confirmed correctly for User. [id=%s]", user.getId())
            );
        }

        if (isEmailAlreadyTaken(user.getEmail())) {
            LOGGER.error("User try to use email that is already taken. [id={}]", user.getId());
            throw new EntityAlreadyExistsException(
                    String.format("User try to use email that is already taken. [id=%s]", user.getId())
            );
        }

        user.addRole(findUserRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
    }

    private boolean arePasswordsNotEquals(final String password, final String confirmationPassword) {
        LOGGER.debug("Checking are entered passwords not equal.");
        return !password.equals(confirmationPassword);
    }

    private boolean isEmailAlreadyTaken(final String email) {
        LOGGER.debug("Checking is entered email already taken.");
        return userRepository.existsByEmail(email);
    }

    private Role findUserRole() {
        final Role userRole = roleService.findByRoleType(RoleType.USER);
        LOGGER.debug("Founded searched Role. [name={}]", userRole.getName().name());
        return userRole;
    }

    @Override
    public User findById(final UUID id) {
        LOGGER.debug("Searching User with given id. [id={}]", id);
        return super.findById(id);
    }

    @Override
    public User findByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find searched User by given email. [email={}]", email);
                    throw new EntityNotFoundException(
                            String.format("Cannot find searched User by given email. [email=%s]", email)
                    );
                });
    }

    @Override
    public List<User> findAll() {
        LOGGER.debug("Searching all Users.");
        return super.findAll();
    }

    @Override
    public User update(final String email, final User user) {
        LOGGER.info("Begin process of updating User. [id={}]", user.getId());
        if (checkEmailAvailability(email, user)) {
            return super.save(user);
        } else {
            LOGGER.error("User with given email already exists. [id={}]", user.getId());
            throw new EntityAlreadyExistsException(
                    String.format("User with given email already exists. [id=%s]", user.getId())
            );
        }
    }

    private boolean checkEmailAvailability(final String email, final User currentUser) {
        LOGGER.debug("Checking is given email available provided by User. [id={}]", currentUser.getId());
        if (!currentUser.getEmail().equals(email)) {
            final List<User> users = findAll();
            LOGGER.debug("Successfully founded all Users.");
            return users.stream()
                    .findFirst()
                    .map(user -> isEmailAvailable(user, currentUser))
                    .orElse(false);
        }

        return true;
    }

    private boolean isEmailAvailable(final User currentUser, final User user) {
        return user.equals(currentUser) || user.getEmail().equals(currentUser.getEmail());
    }
}
