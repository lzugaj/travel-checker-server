package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.mock.RoleMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        final Role userRole = RoleMock.createRole(UUID.randomUUID(), RoleType.USER, "User role");

        user = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        user.setRoles(Collections.singleton(userRole));

        BDDMockito.given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.ofNullable(user));
    }

    @Test
    public void should_Return_User_When_Email_Exists() {
        final UserDetails searchedUserDetails = userDetailsService.loadUserByUsername(user.getEmail());

        Assertions.assertEquals("john.doe@gmail.com", searchedUserDetails.getUsername());
    }

    @Test
    public void should_Throw_Exception_When_User_Not_Exists() {
        BDDMockito.given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("test@gmail.com"));

        final String expectedMessage = String.format("Cannot find searched User by given email. [email=%s]", "test@gmail.com");
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
