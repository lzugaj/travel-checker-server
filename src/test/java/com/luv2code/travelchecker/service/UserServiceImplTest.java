package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.impl.UserServiceImpl;
import com.luv2code.travelchecker.utils.RoleUtil;
import com.luv2code.travelchecker.utils.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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

    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private User fourthUser;
    private User sixthUser;
    private User seventhUser;
    private User eightUser;

    @BeforeEach
    public void setup() {
        // Role
        final Role userRole = RoleUtil.createRole(1L, RoleType.USER, "User role");

        // User
        firstUser = UserUtil.createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968");
        secondUser = UserUtil.createUser(2L, "Sam", "Blanco", "samblanco@gmail.com", "Mustrien");
        thirdUser = UserUtil.createUser(3L, "Sarah", "Isaac", "samblanco@gmail.com", "Mostion");
        fourthUser = UserUtil.createUser(1L, "Ashley", "Ross", "AshleyMRoss@gmail.com", "Mustrien");
        sixthUser = UserUtil.createUser(4L, "Kevin", "Blake", "samblanco@gmail.com", "Kevin");
        seventhUser = UserUtil.createUser(1L, "John", "Doe", "jdoe@gmail.com", "Mone1968");
        eightUser = UserUtil.createUser(1L, "Danny", "Bloom", "dbloom@gmail.com", "dbloom");

        List<User> users = Arrays.asList(secondUser, thirdUser);

        Mockito.when(roleService.findByRoleType(RoleType.USER)).thenReturn(userRole);

        Mockito.when(userRepository.save(firstUser)).thenReturn(firstUser);
        Mockito.when(userRepository.save(eightUser)).thenReturn(eightUser);
        Mockito.when(userRepository.findById(secondUser.getId())).thenReturn(Optional.ofNullable(secondUser));
        // Mockito.when(userRepository.findByEmail(thirdUser.getUsername())).thenReturn(Optional.ofNullable(thirdUser));
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void should_Create_User_When_Username_Does_Not_Exists() {
        final User user = userService.save(firstUser);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("1", String.valueOf(user.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Entity_Username_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.save(fourthUser)
        );

        final String expectedMessage = "Entity 'User' with 'username' value 'Mustrien' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Throw_Exception_When_Entity_Email_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.save(sixthUser)
        );

        final String expectedMessage = "Entity 'User' with 'email' value 'samblanco@gmail.com' already exists.";
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

        final String expectedMessage = "Entity 'User' with 'id' value '1' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_User_When_Username_Is_Present() {
        final User searchedUser = userService.findByEmail(thirdUser.getEmail());

        Assertions.assertNotNull(searchedUser);
        Assertions.assertEquals("3", String.valueOf(thirdUser.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Username_Is_Not_Present() {
        Mockito.when(userRepository.findByEmail(firstUser.getEmail()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByEmail(firstUser.getEmail())
        );

        final String expectedMessage = "Entity 'User' with 'username' value 'Mone1968' not founded.";
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
    public void should_Update_User_When_Username_Is_Valid_And_Not_Exists() {
        final User updatedUser = userService.update(firstUser.getEmail(), eightUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("dbloom", updatedUser.getEmail());
    }

    @Test
    public void should_Update_User_When_Username_Is_Valid_And_Is_Equals() {
        Mockito.when(userRepository.save(seventhUser)).thenReturn(seventhUser);

        final User updatedUser = userService.update(firstUser.getEmail(), seventhUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("Mone1968", updatedUser.getEmail());
    }

    @Test
    public void should_Throw_Exception_When_Username_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.update(firstUser.getEmail(), fourthUser)
        );

        final String expectedMessage = "Entity 'User' with 'username' value 'Mustrien' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
