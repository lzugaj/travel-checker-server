/*
package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.exception.PasswordNotConfirmedRightException;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.impl.UserServiceImpl;
import com.luv2code.travelchecker.util.RoleUtil;
import com.luv2code.travelchecker.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private User fourthUser;
    private User sixthUser;
    private User seventhUser;
    private User eightUser;

    @BeforeEach
    public void setup() {
        final String ENCRYPTED_PASSWORD = "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6";

        // Role
        final Role userRole = RoleUtil.createRole(1L, RoleType.USER, "User role");

        // User
        firstUser = UserUtil.createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        secondUser = UserUtil.createUser(2L, "Sam", "Blanco", "samblanco@gmail.com", "$2a$12$Dibrn62N8/t3fZrEt52R.OtD1Xs4cVY/1rUzx4eI1VncH.K7TwjYC");
        thirdUser = UserUtil.createUser(3L, "Sarah", "Isaac", "samblanco@gmail.com", "$2a$12$GC.zVTQY496Pzo./QJozJOo6EYIQBRAgUCThgHXVTetiGuGkaEEnW\n");
        fourthUser = UserUtil.createUserWithWrongConfirmedPassword(1L, "Ashley", "Ross", "samblanco@gmail.com", "$2a$12$Dibrn62N8/t3fZrEt52R.OtD1Xs4cVY/1rUzx4eI1VncH.K7TwjYC", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        sixthUser = UserUtil.createUser(4L, "Kevin", "Blake", "samblanco@gmail.com", "$2a$12$BH/xk1C8VFWkHXDysIg2NuPlZBvrgWb5lEZWU3N8t7GwiPltz5Vau");
        seventhUser = UserUtil.createUser(1L, "John", "Doe", "jdoe@gmail.com", "$2a$12$KkAF58BD3zE8Qvjmzv/G9.BNF5bkRDWLfgS/MkGiH2VI6qaVNnmmK");
        eightUser = UserUtil.createUser(1L, "Danny", "Bloom", "dbloom@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");

        final List<User> users = Arrays.asList(secondUser, thirdUser);

        Mockito.when(roleService.findByRoleType(RoleType.USER)).thenReturn(userRole);
        Mockito.when(passwordEncoder.encode(firstUser.getPassword())).thenReturn(ENCRYPTED_PASSWORD);
        Mockito.when(userRepository.findById(secondUser.getId())).thenReturn(Optional.ofNullable(secondUser));
        Mockito.when(userRepository.findByEmail(thirdUser.getEmail())).thenReturn(Optional.ofNullable(thirdUser));
        Mockito.when(userRepository.existsByEmail(sixthUser.getEmail())).thenReturn(true);
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void should_Create_User_When_Username_Does_Not_Exists() {
        Mockito.when(userRepository.save(firstUser)).thenReturn(firstUser);

        final User newUser = userService.save(firstUser);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals("eholt@gmail.com", newUser.getEmail());
    }

    @Test
    public void should_Throw_Exception_When_Password_Is_Not_Confirmed_Right() {
        final Exception exception = Assertions.assertThrows(
                PasswordNotConfirmedRightException.class,
                () -> userService.save(fourthUser)
        );

        final String expectedMessage = "Password for User with id: 1 is not confirmed right.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Throw_Exception_When_Entity_Email_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.save(sixthUser)
        );

        final String expectedMessage = "User with email: samblanco@gmail.com already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_User_When_Id_Is_Present() {
        final User searchedUser = userService.findById(secondUser.getId());

        Assertions.assertEquals("2", String.valueOf(searchedUser.getId()));
        Assertions.assertNotNull(searchedUser);
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Present() {
        Mockito.when(userRepository.findById(firstUser.getId()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findById(firstUser.getId())
        );

        final String expectedMessage = "User with id: 1 was not found.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_User_When_Email_Is_Present() {
        final User searchedUser = userService.findByEmail(thirdUser.getEmail());

        Assertions.assertNotNull(searchedUser);
        Assertions.assertEquals("3", String.valueOf(thirdUser.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Email_Is_Not_Present() {
        Mockito.when(userRepository.findByEmail(firstUser.getEmail()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByEmail(firstUser.getEmail())
        );

        final String expectedMessage = "User with email: eholt@gmail.com was not found.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_All_Users() {
        final List<User> searchedUsers = userService.findAll();

        Assertions.assertNotNull(searchedUsers);
        Assertions.assertEquals(2, searchedUsers.size());
    }

    @Test
    public void should_Update_User_When_Email_Is_Valid_And_Not_Exists() {
        Mockito.when(userRepository.save(firstUser)).thenReturn(eightUser);

        final User updatedUser = userService.update(firstUser.getEmail(), eightUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("dbloom@gmail.com", updatedUser.getEmail());
    }

    @Test
    public void should_Update_User_When_Email_Is_Valid_And_Is_Equals() {
        Mockito.when(userRepository.save(seventhUser)).thenReturn(seventhUser);

        final User updatedUser = userService.update(firstUser.getEmail(), seventhUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("jdoe@gmail.com", updatedUser.getEmail());
    }

    @Test
    public void should_Throw_Exception_When_Email_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.update(firstUser.getEmail(), fourthUser)
        );

        final String expectedMessage = "User with email: samblanco@gmail.com already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
*/
