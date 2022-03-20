package com.luv2code.travelchecker.mock;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserMock {

    public static User createUser(final UUID id,
                                  final String firstName,
                                  final String lastName,
                                  final String email,
                                  final String password) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setConfirmationPassword(password);
        user.setCreatedAt(LocalDateTime.now());

        return user;

        /*return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .confirmationPassword(password)
                .createdAt(LocalDateTime.now())
                .build();*/
    }

    public static User createUserWithWrongConfirmedPassword(final UUID id,
                                                            final String firstName,
                                                            final String lastName,
                                                            final String email,
                                                            final String password,
                                                            final String confirmedPassword) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setConfirmationPassword(confirmedPassword);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public static UserPostDto createUserPostDto(final String firstName,
                                                final String lastName,
                                                final String email,
                                                final String password,
                                                final LocalDateTime createdAt) {
        final UserPostDto userPostDto = new UserPostDto();
        userPostDto.setFirstName(firstName);
        userPostDto.setLastName(lastName);
        userPostDto.setEmail(email);
        userPostDto.setPassword(password);
        userPostDto.setConfirmationPassword(password);
        userPostDto.setCreatedAt(createdAt);
        return userPostDto;
    }

    public static UserPutDto createUserPutDto(final String firstName,
                                              final String lastName,
                                              final String email, final
                                              LocalDateTime modifiedAt) {
        final UserPutDto userPutDto = new UserPutDto();
        userPutDto.setFirstName(firstName);
        userPutDto.setLastName(lastName);
        userPutDto.setModifiedAt(modifiedAt);
        return userPutDto;
    }

    public static UserGetDto createUserGetDto(final UUID id,
                                              final String firstName,
                                              final String lastName,
                                              final String email,
                                              final List<RoleGetDto> userDtoRoles,
                                              final List<MarkerGetDto> userDtoMarkers) {
        final UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(id);
        userGetDto.setFirstName(firstName);
        userGetDto.setLastName(lastName);
        userGetDto.setEmail(email);
        userGetDto.setRoles(userDtoRoles);
        userGetDto.setMarkers(userDtoMarkers);
        return userGetDto;
    }
}
