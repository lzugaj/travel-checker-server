/*
package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.impl.UserDetailsServiceImpl;
import com.luv2code.travelchecker.util.RoleUtil;
import com.luv2code.travelchecker.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        final Role userRole = RoleUtil.createRole(1L, RoleType.USER, "User role");

        user = UserUtil.createUser(1L, "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        user.setRoles(Collections.singleton(userRole));

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.ofNullable(user));
    }

    @Test
    public void should_Return_User_When_Email_Exists() {
        final UserDetails searchedUserDetails = userDetailsService.loadUserByUsername(user.getEmail());

        Assertions.assertEquals("john.doe@gmail.com", searchedUserDetails.getUsername());
    }

    @Test
    public void should_Throw_Exception_When_User_Not_Exists() {
        Mockito.when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("test@gmail.com"));

        final String expectedMessage = "User with email: test@gmail.com was not found.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
*/
