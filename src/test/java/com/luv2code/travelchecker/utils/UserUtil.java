package com.luv2code.travelchecker.utils;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;

import java.time.LocalDateTime;
import java.util.List;

public class UserUtil {

    public static User createUser(final Long id, final String firstName, final String lastName, final String email, final String username, final String password) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public static UserPostDto createUserPostDto(final String firstName, final String lastName, final String email, final String username, final String password, final LocalDateTime createdAt) {
        final UserPostDto userPostDto = new UserPostDto();
        userPostDto.setFirstName(firstName);
        userPostDto.setLastName(lastName);
        userPostDto.setEmail(email);
        userPostDto.setUsername(username);
        userPostDto.setPassword(password);
        userPostDto.setCreatedAt(createdAt);
        return userPostDto;
    }

    public static UserPutDto createUserPutDto(final String firstName, final String lastName, final String email, final String username, final LocalDateTime modifiedAt) {
        final UserPutDto userPutDto = new UserPutDto();
        userPutDto.setFirstName(firstName);
        userPutDto.setLastName(lastName);
        userPutDto.setEmail(email);
        userPutDto.setUsername(username);
        userPutDto.setModifiedAt(modifiedAt);
        return userPutDto;
    }

    public static UserGetDto createUserGetDto(final Long id, final String firstName, final String lastName, final String email, final String username, final List<RoleGetDto> userDtoRoles, final List<MarkerGetDto> userDtoMarkers) {
        final UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(id);
        userGetDto.setFirstName(firstName);
        userGetDto.setLastName(lastName);
        userGetDto.setEmail(email);
        userGetDto.setUsername(username);
        userGetDto.setRoles(userDtoRoles);
        userGetDto.setMarkers(userDtoMarkers);
        return userGetDto;
    }
}
