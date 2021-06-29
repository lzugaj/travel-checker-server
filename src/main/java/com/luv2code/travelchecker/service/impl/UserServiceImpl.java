package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserGetDto save(final UserPostDto userPostDto) {
        if (usernameDoesNotExists(userPostDto.getUsername())) {
            final User user = userRepository.save(modelMapper.map(userPostDto, User.class));
            LOGGER.info("Creating new User with id: ´{}´.", user.getId());
            return modelMapper.map(user, UserGetDto.class);
        } else {
            LOGGER.error("User already exists with username: ´{}´.", userPostDto.getUsername());
            throw new EntityAlreadyExistsException(
                    "User", "username", userPostDto.getUsername()
            );
        }
    }

    @Override
    public UserGetDto findById(final Long id) {
        final Optional<User> searchedUser = userRepository.findById(id);
        if (searchedUser.isPresent()) {
            LOGGER.info("Searching User with id: ´{}´.", id);
            return modelMapper.map(searchedUser.get(), UserGetDto.class);
        } else {
            LOGGER.error("Cannot find User with id: ´{}´.", id);
            throw new EntityNotFoundException(
                    "User", "id", String.valueOf(id)
            );
        }
    }

    @Override
    public UserGetDto findByUsername(final String username) {
        final Optional<User> searchedUser = userRepository.findByUsername(username);
        if (searchedUser.isPresent()) {
            LOGGER.info("Searching User with username: ´{}´.", username);
            return modelMapper.map(searchedUser.get(), UserGetDto.class);
        } else {
            LOGGER.error("Cannot find User with username: ´{}´.", username);
            throw new EntityNotFoundException(
                    "User", "username", username
            );
        }
    }

    @Override
    public List<UserGetDto> findAll() {
        final List<User> users = userRepository.findAll();
        final TypeToken<List<UserGetDto>> typeToken = new TypeToken<>() {};
        LOGGER.info("Searching all Users.");
        return modelMapper.map(users, typeToken.getType());
    }

    @Override
    public UserGetDto update(final String username, final UserPutDto newUser) {
        User updatedUser = modelMapper.map(findByUsername(username), User.class);
        if (usernameIsValid(updatedUser, newUser)) {
            updatedUser = userRepository.save(modelMapper.map(newUser, User.class));
            LOGGER.info("Updating User with id: ´{}´.", updatedUser.getId());
            return modelMapper.map(updatedUser, UserGetDto.class);
        } else {
            LOGGER.error("User already exists with username: ´{}´.", newUser.getUsername());
            throw new EntityAlreadyExistsException("User", "username", newUser.getUsername());
        }
    }

    private boolean usernameIsValid(final User updatedUser, final UserPutDto newUser) {
        if (!newUser.getUsername().equals(updatedUser.getUsername())) {
            return usernameDoesNotExists(newUser.getUsername());
        }

        return true;
    }

    private boolean usernameDoesNotExists(final String username) {
        final List<UserGetDto> users = findAll();
        return users.stream()
                .noneMatch(user -> user.getUsername().equals(username));
    }
}
