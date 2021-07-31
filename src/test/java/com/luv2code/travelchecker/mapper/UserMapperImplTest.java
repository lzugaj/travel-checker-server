package com.luv2code.travelchecker.mapper;

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
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.mapper.impl.UserMapperImpl;
import com.luv2code.travelchecker.service.RoleService;
import com.luv2code.travelchecker.utils.CoordinateUtil;
import com.luv2code.travelchecker.utils.MarkerUtil;
import com.luv2code.travelchecker.utils.RoleUtil;
import com.luv2code.travelchecker.utils.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class UserMapperImplTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    @Mock
    private RoleService roleService;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private MarkerMapper markerMapper;

    @Mock
    private ModelMapper modelMapper;

    private User firstUser;
    private User secondUser;

    private UserPostDto userPostDto;

    private UserPutDto userPutDto;

    private List<User> users;

    @BeforeEach
    public void setup() {
        // Role
        Role role = RoleUtil.createRole(1L, RoleType.USER, "User role");
        List<Role> roles = Collections.singletonList(role);

        // RoleGetDto
        RoleGetDto roleGetDto = RoleUtil.createRoleGetDto(role);
        List<RoleGetDto> dtoRoles = Collections.singletonList(roleGetDto);

        // Coordinate
        Coordinate firstCoordinate = CoordinateUtil.createCoordinate(1L, -0.127758, 51.507351);
        Coordinate secondCoordinate = CoordinateUtil.createCoordinate(2L, -74.005974, 40.712776);

        // CoordinateGetDto
        CoordinateGetDto firstCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(firstCoordinate.getId(), firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        CoordinateGetDto secondCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(secondCoordinate.getId(), secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        // Marker
        Marker firstMarker = MarkerUtil.createMarker(1L, "London", "London is a busy and beautiful city", LocalDate.now(), 4, Boolean.TRUE, LocalDateTime.now(), firstCoordinate);
        Marker secondMarker = MarkerUtil.createMarker(2L, "New York", "New York is too big city to explore", LocalDate.now(), 5, Boolean.FALSE, LocalDateTime.now(), secondCoordinate);

        // MarkerGetDto
        MarkerGetDto firstMarkerGetDto = MarkerUtil.createMarkerGetDto(firstMarker.getId(), firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstCoordinateGetDto);
        MarkerGetDto secondMarkerGetDto = MarkerUtil.createMarkerGetDto(secondMarker.getId(), secondMarker.getName(), secondMarker.getDescription(), secondMarker.getEventDate(), secondMarker.getGrade(), secondMarker.getShouldVisitAgain(), secondCoordinateGetDto);
        List<MarkerGetDto> userDtoMarkers = Arrays.asList(firstMarkerGetDto, secondMarkerGetDto);

        // User
        firstUser = UserUtil.createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968", "tu3ze9ooQu");
        secondUser = UserUtil.createUser(2L, "Sam", "Blanco", "samblanco@gmail.com", "Mustrien", "goh7DuoF5");

        // UserPostDto
        userPostDto = UserUtil.createUserPostDto(firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), firstUser.getPassword(), LocalDateTime.now());

        // UserPutDto
        userPutDto = UserUtil.createUserPutDto("Jim", "Gonzalez", "JimBGonzalez@gmail.com", "Cend1997", LocalDateTime.now());

        // UserGetDto
        UserGetDto firstUserGetDto = UserUtil.createUserGetDto(firstUser.getId(), firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), dtoRoles, null);
        UserGetDto secondUserGetDto = UserUtil.createUserGetDto(secondUser.getId(), secondUser.getFirstName(), secondUser.getLastName(), secondUser.getEmail(), secondUser.getUsername(), dtoRoles, userDtoMarkers);

        users = Arrays.asList(firstUser, secondUser);

        Mockito.when(roleService.findByRoleType(RoleType.USER)).thenReturn(role);
        Mockito.when(roleMapper.entitiesToDto(roles)).thenReturn(dtoRoles);
        Mockito.when(markerMapper.entitiesToDto(secondUser.getMarkers())).thenReturn(userDtoMarkers);

        Mockito.when(modelMapper.map(userPostDto, User.class)).thenReturn(firstUser);
        Mockito.when(modelMapper.map(firstUser, UserGetDto.class)).thenReturn(firstUserGetDto);
        Mockito.when(modelMapper.map(secondUser, UserGetDto.class)).thenReturn(secondUserGetDto);
    }

    @Test
    @DisplayName("dtoToEntity(UserPostDto) - should return User")
    public void should_Return_User_When_UserPostDto_Correctly_Mapped() {
        final User user = userMapper.dtoToEntity(userPostDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("1", String.valueOf(user.getId()));
    }

    @Test
    @DisplayName("dtoToEntity(User, UserPutDto) - should return User")
    public void should_Return_User_When_UserPutDto_Correctly_Mapped() {
        final User user = userMapper.dtoToEntity(firstUser, userPutDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("1", String.valueOf(user.getId()));
    }

    @Test
    @DisplayName("entityToDto(User) - should return UserGetDto")
    public void should_Return_UserGetDto_When_User_Correctly_Mapped() {
        final UserGetDto userGetDto = userMapper.entityToDto(secondUser);

        Assertions.assertNotNull(userGetDto);
        Assertions.assertEquals("2", String.valueOf(userGetDto.getId()));
    }

    @Test
    @DisplayName("entitiesToDto(List<User>) - should return UserGetDto list")
    public void should_Return_UserGetDto_List_When_Users_Correctly_Mapped() {
        final List<UserGetDto> dtoUsers = userMapper.entitiesToDto(users);

        Assertions.assertNotNull(dtoUsers);
        Assertions.assertEquals(2, dtoUsers.size());
    }
}
