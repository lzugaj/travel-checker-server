package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.password.PasswordUpdateDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.exception.PasswordNotConfirmedRightException;
import com.luv2code.travelchecker.exception.PasswordNotEnteredRightException;
import com.luv2code.travelchecker.mapper.UserMapper;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private UserMapper userMapper;

    private Role userRole;

    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;

    private Marker firstMarker;
    private Marker secondMarker;

    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private User fourthUser;
    private User fifthUser;

    private UserPostDto firstUserPostDto;
    private UserPostDto secondUserPostDto;

    private UserPutDto firstUserPutDto;
    private UserPutDto secondUserPutDto;
    private UserPutDto thirdUserPutDto;

    private PasswordUpdateDto firstPasswordUpdateDto;
    private PasswordUpdateDto secondPasswordUpdateDto;
    private PasswordUpdateDto thirdPasswordUpdateDto;

    @BeforeEach
    public void setup() {
        // Role
        userRole = createRole(1L, RoleType.USER, "USER role could READ and WRITE data which is assigned only to them");

        // Coordinate
        firstCoordinate = createCoordinate(1L, -0.127758, 51.507351);
        secondCoordinate = createCoordinate(2L, -74.005974, 40.712776);

        // Marker
        firstMarker = createMarker(1L, "London", "London is a busy and beautiful city", LocalDate.now(), 4, Boolean.TRUE, LocalDateTime.now(), null, firstCoordinate);
        secondMarker = createMarker(2L, "New York", "New York is too big city to explore", LocalDate.now(), 5, Boolean.FALSE, LocalDateTime.now(), null, secondCoordinate);

        final List<Marker> markers = Arrays.asList(firstMarker, secondMarker);

        // User
        firstUser = createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968", "tu3ze9ooQu", LocalDateTime.now(), markers, userRole);
        secondUser = createUser(2L, "Sam", "Blanco", "samblanco@gmail.com", "Mustrien", "goh7DuoF5", LocalDateTime.now(), null, userRole);
        thirdUser = createUser(3L, "Sarah", "Isaac", "SarahLIsaac@gmail.com", "Mostion", "urai9Shahnu", LocalDateTime.now(), null, userRole);
        fourthUser = createUser(1L, "Ashley", "Ross", "AshleyMRoss@gmail.com", "Hargent", firstUser.getPassword(), LocalDateTime.now(), markers, userRole);
        fifthUser = createUser(1L, "Edith", "Murley", "EdithLMurley@gmail.com", firstUser.getUsername(), firstUser.getPassword(), LocalDateTime.now(), markers, userRole);

        // UserPostDto
        firstUserPostDto = createUserPostDto(firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), firstUser.getPassword(), LocalDateTime.now());
        secondUserPostDto = createUserPostDto("James", "Blane", "JamesBLane@gmail.com", secondUser.getUsername(), "aefahx0Aihoo", LocalDateTime.now());

        // UserPutDto
        firstUserPutDto = createUserPutDto(fourthUser.getFirstName(), fourthUser.getLastName(), fourthUser.getEmail(), firstUser.getUsername(), LocalDateTime.now());
        secondUserPutDto = createUserPutDto(fifthUser.getFirstName(), fifthUser.getLastName(), fifthUser.getEmail(), thirdUser.getUsername(), LocalDateTime.now());
        thirdUserPutDto = createUserPutDto(fifthUser.getFirstName(), fifthUser.getLastName(), fifthUser.getEmail(), secondUser.getUsername(), LocalDateTime.now());;

        // PasswordUpdateDto
        firstPasswordUpdateDto = createPasswordUpdateDto(firstUser.getPassword(), "password1234", "password1234");
        secondPasswordUpdateDto = createPasswordUpdateDto("Tu3ze9ooQu", "password1234", "password1234");
        thirdPasswordUpdateDto = createPasswordUpdateDto(firstUser.getPassword(), "password1234", "passworD1234");

        final List<User> users = Arrays.asList(secondUser, thirdUser);

        Mockito.when(userMapper.dtoToEntity(firstUserPostDto)).thenReturn(firstUser);
        Mockito.when(userMapper.dtoToEntity(firstUser, firstUserPutDto)).thenReturn(fourthUser);
        Mockito.when(userMapper.dtoToEntity(firstUser, secondUserPutDto)).thenReturn(fifthUser);

        Mockito.when(userRepository.save(firstUser)).thenReturn(firstUser);
        Mockito.when(userRepository.findById(secondUser.getId())).thenReturn(Optional.ofNullable(secondUser));
        Mockito.when(userRepository.findByUsername(thirdUser.getUsername())).thenReturn(Optional.ofNullable(thirdUser));
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void should_Create_User_When_Username_Does_Not_Exists() {
        final User user = userService.save(firstUserPostDto);

        Assertions.assertEquals("Mone1968", user.getUsername());
        Assertions.assertNotNull(user);
    }

    @Test
    public void should_Throw_Exception_When_Entity_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.save(secondUserPostDto)
        );

        final String expectedMessage = "Entity 'User' with 'username' value 'Mustrien' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_User_When_Id_Is_Present() {
        final User searchedUser = userService.findById(secondUser.getId());

        Assertions.assertEquals("Mustrien", searchedUser.getUsername());
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
        final User searchedUser = userService.findByUsername(thirdUser.getUsername());

        Assertions.assertEquals("Mostion", searchedUser.getUsername());
        Assertions.assertNotNull(searchedUser);
    }

    @Test
    public void should_Throw_Exception_When_Username_Is_Not_Present() {
        Mockito.when(userRepository.findByUsername(firstUser.getUsername()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByUsername(firstUser.getUsername())
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
        final User updatedUser = userService.update(firstUser, firstUserPutDto);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("Hargent", updatedUser.getUsername());
    }

    @Test
    public void should_Throw_Exception_When_Username_Already_Exists() {
        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.update(secondUser, secondUserPutDto)
        );

        final String expectedMessage = "Entity 'User' with 'username' value 'Mostion' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Change_User_Password_When_Everything_Is_Entered_Correctly() {
        final User user = userService.changePassword(firstUser, firstPasswordUpdateDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("password1234", user.getPassword());
    }

    @Test
    public void should_Throw_Exception_When_Password_Not_Entered_Right() {
        final Exception exception = Assertions.assertThrows(
                PasswordNotEnteredRightException.class,
                () -> userService.changePassword(firstUser, secondPasswordUpdateDto)
        );

        final String expectedMessage = "Entity 'User' with 'password' value 'Tu3ze9ooQu' is not entered right.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Throw_Exception_When_Password_Not_Confirmed_Right() {
        final Exception exception = Assertions.assertThrows(
                PasswordNotConfirmedRightException.class,
                () -> userService.changePassword(firstUser, thirdPasswordUpdateDto)
        );

        final String expectedMessage = "Entity 'User' with 'password' value 'password1234' is not confirmed right.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    private Role createRole(final Long id, final RoleType roleType, final String description) {
        final Role role = new Role();
        role.setId(id);
        role.setName(roleType);
        role.setDescription(description);
        return role;
    }

    private Coordinate createCoordinate(final Long id, final Double longitude, final Double latitude) {
        final Coordinate coordinate = new Coordinate();
        coordinate.setId(id);
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        return coordinate;
    }

    private Marker createMarker(final Long id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final LocalDateTime createdAt, final LocalDateTime modifiedAt, final Coordinate coordinate) {
        final Marker marker = new Marker();
        marker.setId(id);
        marker.setName(name);
        marker.setDescription(description);
        marker.setEventDate(eventDate);
        marker.setGrade(grade);
        marker.setShouldVisitAgain(shouldVisitAgain);
        marker.setCreatedAt(createdAt);
        marker.setModifiedAt(modifiedAt);
        marker.setCoordinate(coordinate);
        return marker;
    }

    private User createUser(final Long id, final String firstName, final String lastName, final String email, final String username, final String password, final LocalDateTime createdAt, final List<Marker> markers, final Role userRole) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setCreatedAt(LocalDateTime.now());
        user.setModifiedAt(null);
        user.setMarkers(markers);
        user.addRole(userRole);
        return user;
    }

    private UserPostDto createUserPostDto(final String firstName, final String lastName, final String email, final String username, final String password, final LocalDateTime createdAt) {
        final UserPostDto userPostDto = new UserPostDto();
        userPostDto.setFirstName(firstName);
        userPostDto.setLastName(lastName);
        userPostDto.setEmail(email);
        userPostDto.setUsername(username);
        userPostDto.setPassword(password);
        userPostDto.setCreatedAt(createdAt);
        return userPostDto;
    }

    private UserPutDto createUserPutDto(final String firstName, final String lastName, final String email, final String username, final LocalDateTime modifiedAt) {
        final UserPutDto userPutDto = new UserPutDto();
        userPutDto.setFirstName(firstName);
        userPutDto.setLastName(lastName);
        userPutDto.setEmail(email);
        userPutDto.setUsername(username);
        userPutDto.setModifiedAt(modifiedAt);
        return userPutDto;
    }

    private PasswordUpdateDto createPasswordUpdateDto(final String oldPassword, final String newPassword, final String confirmedNewPassword) {
        final PasswordUpdateDto passwordUpdateDto = new PasswordUpdateDto();
        passwordUpdateDto.setOldPassword(oldPassword);
        passwordUpdateDto.setNewPassword(newPassword);
        passwordUpdateDto.setConfirmedNewPassword(confirmedNewPassword);
        return passwordUpdateDto;
    }
}
