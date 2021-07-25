package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.PasswordUpdateDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.exception.PasswordNotConfirmedRightException;
import com.luv2code.travelchecker.exception.PasswordNotEnteredRightException;
import com.luv2code.travelchecker.mapper.UserMapper;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(final UserPostDto userPostDto) {
        if (checkDoesUsernameNotExists(userPostDto.getUsername())) {
            final User user = userRepository.save(userMapper.dtoToEntity(userPostDto));
            LOGGER.info("Creating new User with username: ´{}´.", userPostDto.getUsername());
            return user;
        } else {
            LOGGER.error("User already exists with username: ´{}´.", userPostDto.getUsername());
            throw new EntityAlreadyExistsException(
                    "User", "username", userPostDto.getUsername());
        }
    }

    @Override
    public User findById(final Long id) {
        final Optional<User> searchedUser = userRepository.findById(id);
        if (searchedUser.isPresent()) {
            LOGGER.info("Searching User with id: ´{}´.", id);
            return searchedUser.get();
        } else {
            LOGGER.error("Cannot find User with id: ´{}´.", id);
            throw new EntityNotFoundException(
                    "User", "id", String.valueOf(id));
        }
    }

    @Override
    public User findByUsername(final String username) {
        final Optional<User> searchedUser = userRepository.findByUsername(username);
        if (searchedUser.isPresent()) {
            LOGGER.info("Searching User with username: ´{}´.", username);
            return searchedUser.get();
        } else {
            LOGGER.error("Cannot find User with username: ´{}´.", username);
            throw new EntityNotFoundException(
                    "User", "username", username);
        }
    }

    @Override
    public List<User> findAll() {
        final List<User> searchedUsers = userRepository.findAll();
        LOGGER.info("Searching all Users.");
        return searchedUsers;
    }

    @Override
    public User update(final User oldUser, final UserPutDto newUser) {
        if (checkDoesUsernamesAreEqual(oldUser, newUser)) {
            final User updatedUser = userMapper.dtoToEntity(oldUser, newUser);
            userRepository.save(updatedUser);
            LOGGER.info("Updating User with username: ´{}´.", oldUser.getUsername());
            return updatedUser;
        } else {
            LOGGER.error("User already exists with username: ´{}´.", newUser.getUsername());
            throw new EntityAlreadyExistsException(
                    "User", "username", newUser.getUsername());
        }
    }

    private boolean checkDoesUsernamesAreEqual(final User oldUser, final UserPutDto newUser) {
        if (!newUser.getUsername().equals(oldUser.getUsername())) {
            return checkDoesUsernameNotExists(newUser.getUsername());
        }

        return true;
    }

    private boolean checkDoesUsernameNotExists(final String username) {
        final List<User> searchedUsers = findAll();
        return searchedUsers.stream()
                .noneMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public User changePassword(final User oldUser, final PasswordUpdateDto passwordUpdateDto) {
        if (checkIsPasswordEnteredCorrect(oldUser, passwordUpdateDto)) {
            if (checkIsPasswordConfirmedRight(passwordUpdateDto)) {
                LOGGER.info("Updating new password for User with username: ´{}´.", oldUser.getUsername());
                oldUser.setPassword(passwordUpdateDto.getNewPassword());
                return userRepository.save(oldUser);
            } else {
                LOGGER.error("Password not confirmed right for Entity with username: ´{}´.", oldUser.getUsername());
                throw new PasswordNotConfirmedRightException(
                        "User", "password", passwordUpdateDto.getNewPassword());
            }
        } else {
            LOGGER.error("Password not entered right for Entity with username: ´{}´.", oldUser.getUsername());
            throw new PasswordNotEnteredRightException(
                    "User", "password", passwordUpdateDto.getOldPassword());
        }
    }

    private boolean checkIsPasswordEnteredCorrect(final User oldUser, final PasswordUpdateDto passwordUpdateDto) {
        return oldUser.getPassword().equals(passwordUpdateDto.getOldPassword());
    }

    private boolean checkIsPasswordConfirmedRight(final PasswordUpdateDto passwordUpdateDto) {
        return passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getConfirmedNewPassword());
    }
}
