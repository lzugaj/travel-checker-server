package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User firstUser;
    private User secondUser;
    private User thirdUser;

    private UserPostDto firstUserPostDto;
    private UserPostDto secondUserPostDto;
    private UserPostDto thirdUserPostDto;

    private UserGetDto firstUserGetDto;
    private UserGetDto secondUserGetDto;
    private UserGetDto thirdUserGetDto;

    @BeforeEach
    public void setup() {
        // Role
        final Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName(RoleType.USER);
        userRole.setDescription("USER role could READ and WRITE data which is assigned only to them");

        final RoleGetDto userGetDto = new RoleGetDto();
        userGetDto.setName(userRole.getName().name());

        final List<RoleGetDto> rolesGetDto = new ArrayList<>();
        rolesGetDto.add(userGetDto);

        // Coordinate
        final Coordinate coordinate = new Coordinate();
        coordinate.setId(1L);
        coordinate.setLatitude(45.815399);
        coordinate.setLongitude(15.966568);

        final CoordinateGetDto coordinateGetDto = new CoordinateGetDto();
        coordinateGetDto.setLatitude(coordinate.getLatitude());
        coordinateGetDto.setLongitude(coordinate.getLongitude());

        // Marker
        final Marker marker = new Marker();
        marker.setId(1L);
        marker.setName("Zagreb");
        marker.setDescription("Description 1");
        marker.setEventDate(LocalDate.now());
        marker.setGrade(4);
        marker.setShouldVisitAgain(Boolean.TRUE);
        marker.setCreatedAt(LocalDateTime.now());
        marker.setModifiedAt(null);
        marker.setCoordinate(coordinate);

        final MarkerGetDto markerGetDto = new MarkerGetDto();
        markerGetDto.setId(marker.getId());
        markerGetDto.setName(marker.getName());
        markerGetDto.setDescription(marker.getDescription());
        markerGetDto.setEventDate(marker.getEventDate());
        markerGetDto.setGrade(marker.getGrade());
        markerGetDto.setShouldVisitAgain(marker.getShouldVisitAgain());
        markerGetDto.setCoordinate(coordinateGetDto);

        final List<Marker> markers = new ArrayList<>();
        markers.add(marker);

        final List<MarkerGetDto> markersGetDto = new ArrayList<>();
        markersGetDto.add(markerGetDto);

        // User
        firstUser = new User();
        firstUser.setId(1L);
        firstUser.setFirstName("Michael");
        firstUser.setLastName("Jordan");
        firstUser.setEmail("chicago@bulls23.com");
        firstUser.setUsername("mjordan");
        firstUser.setPassword("mj23");
        firstUser.setCreatedAt(LocalDateTime.now());
        firstUser.setModifiedAt(null);
        firstUser.setMarkers(markers);
        firstUser.addRole(userRole);

        firstUserPostDto = new UserPostDto();
        firstUserPostDto.setFirstName(firstUser.getFirstName());
        firstUserPostDto.setLastName(firstUser.getLastName());
        firstUserPostDto.setEmail(firstUser.getEmail());
        firstUserPostDto.setUsername(firstUser.getUsername());
        firstUserPostDto.setPassword(firstUser.getPassword());

        firstUserGetDto = new UserGetDto();
        firstUserGetDto.setFirstName(firstUser.getFirstName());
        firstUserGetDto.setLastName(firstUser.getLastName());
        firstUserGetDto.setEmail(firstUser.getEmail());
        firstUserGetDto.setUsername(firstUser.getUsername());
        firstUserGetDto.setRoles(rolesGetDto);
        firstUserGetDto.setMarkers(markersGetDto);

        secondUser = new User();
        secondUser.setId(2L);
        secondUser.setFirstName("Kobe");
        secondUser.setLastName("Bryant");
        secondUser.setEmail("lalakers@kobe24.com");
        secondUser.setUsername("blackmamba");
        secondUser.setPassword("kobebryant");
        secondUser.setCreatedAt(LocalDateTime.now());
        secondUser.setModifiedAt(null);
        secondUser.setMarkers(null);
        secondUser.addRole(userRole);

        secondUserPostDto = new UserPostDto();
        secondUserPostDto.setFirstName(secondUser.getFirstName());
        secondUserPostDto.setLastName(secondUser.getLastName());
        secondUserPostDto.setEmail(secondUser.getEmail());
        secondUserPostDto.setUsername(secondUser.getUsername());
        secondUserPostDto.setPassword(secondUser.getPassword());

        secondUserGetDto = new UserGetDto();
        secondUserGetDto.setFirstName(secondUser.getFirstName());
        secondUserGetDto.setLastName(secondUser.getLastName());
        secondUserGetDto.setEmail(secondUser.getEmail());
        secondUserGetDto.setUsername(secondUser.getUsername());
        secondUserGetDto.setRoles(rolesGetDto);
        secondUserGetDto.setMarkers(null);

        thirdUser = new User();
        thirdUser.setId(3L);
        thirdUser.setFirstName("Lebron");
        thirdUser.setLastName("James");
        thirdUser.setEmail("clevland@miami.com");
        thirdUser.setUsername("blackmamba");
        thirdUser.setPassword("lbj23");
        thirdUser.setCreatedAt(LocalDateTime.now());
        thirdUser.setModifiedAt(null);
        thirdUser.setMarkers(null);
        thirdUser.addRole(userRole);

        thirdUserPostDto = new UserPostDto();
        thirdUserPostDto.setFirstName(thirdUser.getFirstName());
        thirdUserPostDto.setLastName(thirdUser.getLastName());
        thirdUserPostDto.setEmail(thirdUser.getEmail());
        thirdUserPostDto.setUsername(thirdUser.getUsername());
        thirdUserPostDto.setPassword(thirdUser.getPassword());

        thirdUserGetDto = new UserGetDto();
        thirdUserGetDto.setFirstName(thirdUser.getFirstName());
        thirdUserGetDto.setLastName(thirdUser.getLastName());
        thirdUserGetDto.setEmail(thirdUser.getEmail());
        thirdUserGetDto.setUsername(thirdUser.getUsername());
        thirdUserGetDto.setRoles(rolesGetDto);
        thirdUserGetDto.setMarkers(null);

        final List<User> users = new ArrayList<>();
        users.add(secondUser);
        users.add(thirdUser);

        final List<UserGetDto> usersGetDto = new ArrayList<>();
        usersGetDto.add(secondUserGetDto);
        usersGetDto.add(thirdUserGetDto);

        final TypeToken<List<UserGetDto>> typeToken = new TypeToken<>() {};

        Mockito.when(modelMapper.map(firstUserPostDto, User.class)).thenReturn(firstUser);
        Mockito.when(modelMapper.map(firstUser, UserGetDto.class)).thenReturn(firstUserGetDto);
        Mockito.when(modelMapper.map(secondUser, UserGetDto.class)).thenReturn(secondUserGetDto);
        Mockito.when(modelMapper.map(thirdUser, UserGetDto.class)).thenReturn(thirdUserGetDto);
        Mockito.when(modelMapper.map(users, typeToken.getType())).thenReturn(usersGetDto);

        Mockito.when(userRepository.save(firstUser)).thenReturn(firstUser);
        Mockito.when(userRepository.findById(secondUser.getId())).thenReturn(Optional.ofNullable(secondUser));
        Mockito.when(userRepository.findByUsername(thirdUser.getUsername())).thenReturn(Optional.ofNullable(thirdUser));
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void should_Create_User_When_Username_Does_Not_Exists() {
        final UserGetDto user = userService.save(firstUserPostDto);

        Assertions.assertEquals("mjordan", user.getUsername());
        Assertions.assertNotNull(user);
    }

    @Test
    public void should_Throw_Exception_When_Entity_Already_Exists() {
        Mockito.when(userRepository.save(modelMapper.map(secondUserPostDto, User.class)))
                .thenThrow(new EntityAlreadyExistsException(
                        "User", "username", secondUserPostDto.getUsername()
                ));

        final Exception exception = Assertions.assertThrows(
                EntityAlreadyExistsException.class,
                () -> userService.save(secondUserPostDto));

        final String expectedMessage = "Entity 'User' with 'username' value 'blackmamba' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_User_When_Id_Is_Present() {
        final UserGetDto userGetDto = userService.findById(secondUser.getId());

        Assertions.assertEquals("blackmamba", userGetDto.getUsername());
        Assertions.assertNotNull(userGetDto);
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Present() {
        Mockito.when(userRepository.findById(firstUser.getId()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findById(firstUser.getId()));

        final String expectedMessage = "Entity 'User' with 'id' value '1' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_User_When_Username_Is_Present() {
        final UserGetDto userGetDto = userService.findByUsername(thirdUser.getUsername());

        Assertions.assertEquals("blackmamba", userGetDto.getUsername());
        Assertions.assertNotNull(userGetDto);
    }

    @Test
    public void should_Throw_Exception_When_Username_Is_Not_Present() {
        Mockito.when(userRepository.findByUsername(firstUser.getUsername()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByUsername(firstUser.getUsername()));

        final String expectedMessage = "Entity 'User' with 'username' value 'mjordan' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_All_Users() {
        final List<UserGetDto> usersGetDto = userService.findAll();

        Assertions.assertNotNull(usersGetDto);
        Assertions.assertEquals(3, usersGetDto.size());
    }
//
//    @Test
//    public void should_Update_User_When_Username_Is_Valid() {
//        final UserGetDto updatedUser = userService.update(fourthUser, firstUserPutDto);
//
//        Assertions.assertEquals("mjordan", updatedUser.getUsername());
//        Assertions.assertNotNull(updatedUser);
//    }
//
//    @Test
//    public void should_Throw_Exception_When_Username_Already_Exists() {
//        Mockito.when(userRepository.save(userMapper.entityDtoToEntity(secondUser, secondUserPutDto)))
//                .thenThrow(new EntityAlreadyExistsException(
//                        "User", "username", secondUserPutDto.getUsername()
//                ));
//
//        final Exception exception = Assertions.assertThrows(
//                EntityAlreadyExistsException.class,
//                () -> userService.update(secondUser, secondUserPutDto));
//
//        final String expectedMessage = "Entity 'User' with 'username' value 'russwest' already exists.";
//        final String actualMessage = exception.getMessage();
//
//        Assertions.assertEquals(expectedMessage, actualMessage);
//    }
}
