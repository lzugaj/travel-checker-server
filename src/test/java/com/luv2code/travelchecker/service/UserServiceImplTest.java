package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.exception.PasswordNotConfirmedRightException;
import com.luv2code.travelchecker.mock.RoleMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private User ninthUser;

    @BeforeEach
    public void setup() {
        final String ENCRYPTED_PASSWORD = "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6";

        // Role
        final Role userRole = RoleMock.createRole(UUID.randomUUID(), RoleType.USER, "User role");

        // User
        firstUser = UserMock.createUser(UUID.fromString("1aa1b7ae-ec30-431b-9473-07e9a5bb0651"), "Eunice", "Holt", "eholt@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        secondUser = UserMock.createUser(UUID.fromString("3211b7ae-ec30-431b-9473-07e9a5bb0651"), "Sam", "Blanco", "samblanco@gmail.com", "$2a$12$Dibrn62N8/t3fZrEt52R.OtD1Xs4cVY/1rUzx4eI1VncH.K7TwjYC");
        thirdUser = UserMock.createUser(UUID.fromString("f2d8d3f6-0700-4840-a18f-cb9d8ed9c17a"), "Sarah", "Isaac", "sarah.isaac@gmail.com", "$2a$12$GC.zVTQY496Pzo./QJozJOo6EYIQBRAgUCThgHXVTetiGuGkaEEnW");
        fourthUser = UserMock.createUserWithWrongConfirmedPassword(UUID.fromString("1aa1b7ae-ec30-431b-9473-07e9a5bb0651"), "Ashley", "Ross", "ashley.ross@gmail.com", "$2a$12$Dibrn62N8/t3fZrEt52R.OtD1Xs4cVY/1rUzx4eI1VncH.K7TwjYC", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        sixthUser = UserMock.createUser(UUID.fromString("455fbb87-2e43-49a6-aa59-c42081541120"), "Kevin", "Blake", "samblanco@gmail.com", "$2a$12$BH/xk1C8VFWkHXDysIg2NuPlZBvrgWb5lEZWU3N8t7GwiPltz5Vau");
        seventhUser = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "eholt@gmail.com", "$2a$12$KkAF58BD3zE8Qvjmzv/G9.BNF5bkRDWLfgS/MkGiH2VI6qaVNnmmK");
        eightUser = UserMock.createUser(UUID.fromString("1aa1b7ae-ec30-431b-9473-07e9a5bb0651"), "Danny", "Bloom", "dbloom@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        ninthUser = UserMock.createUser(UUID.fromString("3211b7ae-ec30-431b-1234-07e9a5bb0651"), "John", "Doe", "sarah.isaac@gmail.com", "$2a$12$Dibrn62N8/t3fZrEt52R.OtD1Xs4cVY/1rUzx4eI1VncH.K7TwjYC");

        final List<User> users = Arrays.asList(secondUser, thirdUser);

        BDDMockito.given(roleService.findByRoleType(RoleType.USER)).willReturn(userRole);
        BDDMockito.given(passwordEncoder.encode(firstUser.getPassword())).willReturn(ENCRYPTED_PASSWORD);
        BDDMockito.given(userRepository.findById(secondUser.getId())).willReturn(Optional.ofNullable(secondUser));
        BDDMockito.given(userRepository.findByEmail(thirdUser.getEmail())).willReturn(Optional.ofNullable(thirdUser));
        BDDMockito.given(userRepository.findAll()).willReturn(users);
    }

    @Test
    public void should_Create_User_When_Username_Does_Not_Exists() {
        BDDMockito.given(userRepository.save(firstUser)).willReturn(firstUser);

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

        final String expectedMessage = String.format("Password is not confirmed correctly for User. [id=%s]", fourthUser.getId());
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Throw_Exception_When_Entity_Email_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.save(sixthUser)
        );

        final String expectedMessage = String.format("User try to use email that is already taken. [id=%s]", sixthUser.getId());
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_User_When_Id_Is_Present() {
        final User searchedUser = userService.findById(secondUser.getId());

        Assertions.assertEquals("3211b7ae-ec30-431b-9473-07e9a5bb0651", String.valueOf(searchedUser.getId()));
        Assertions.assertNotNull(searchedUser);
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Present() {
        BDDMockito.given(userRepository.findById(firstUser.getId()))
                .willReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findById(firstUser.getId())
        );

        final String expectedMessage = String.format("Cannot find searched User by given id. [id=%s]", firstUser.getId());
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_User_When_Email_Is_Present() {
        final User searchedUser = userService.findByEmail(thirdUser.getEmail());

        Assertions.assertNotNull(searchedUser);
        Assertions.assertEquals("sarah.isaac@gmail.com", thirdUser.getEmail());
    }

    @Test
    public void should_Throw_Exception_When_Email_Is_Not_Present() {
        BDDMockito.given(userRepository.findByEmail(firstUser.getEmail()))
                .willReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByEmail(firstUser.getEmail())
        );

        final String expectedMessage = String.format("Cannot find searched User by given email. [email=%s]", firstUser.getEmail());
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
        BDDMockito.given(userRepository.save(firstUser)).willReturn(eightUser);

        final User updatedUser = userService.update(firstUser.getEmail(), eightUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("dbloom@gmail.com", updatedUser.getEmail());
    }

    @Test
    public void should_Update_User_When_Email_Is_Valid_And_Is_Equals() {
        BDDMockito.given(userRepository.save(seventhUser)).willReturn(seventhUser);

        final User updatedUser = userService.update(firstUser.getEmail(), seventhUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("eholt@gmail.com", updatedUser.getEmail());
    }

    @Test
    public void should_Throw_Exception_When_Email_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.update(secondUser.getEmail(), ninthUser)
        );

        final String expectedMessage = String.format("User with given email already exists. [id=%s]", ninthUser.getId());
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
