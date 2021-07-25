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

    private Role userRole;

    private RoleGetDto firstUserRoleDto;

    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;

    private CoordinateGetDto firstCoordinateGetDto;
    private CoordinateGetDto secondCoordinateGetDto;

    private Marker firstMarker;
    private Marker secondMarker;

    private MarkerGetDto firstMarkerGetDto;
    private MarkerGetDto secondMarkerGetDto;

    private User firstUser;

    private UserGetDto userGetDto;
    private UserPostDto userPostDto;
    private UserPutDto userPutDto;

    private List<Role> roles;
    private List<RoleGetDto> userDtoRoles;
    private List<Marker> markers;
    private List<MarkerGetDto> userDtoMarkers;
    private List<User> users;

    @BeforeEach
    public void setup() {
        // Role
        userRole = createRole(1L, RoleType.USER, "User role");
        roles = Collections.singletonList(userRole);

        // UserRoleDto
        firstUserRoleDto = createUserRoleDto(userRole);
        userDtoRoles = Collections.singletonList(firstUserRoleDto);

        // Coordinate
        firstCoordinate = createCoordinate(1L, -0.127758, 51.507351);
        secondCoordinate = createCoordinate(2L, -74.005974, 40.712776);

        // CoordinateGetDto
        firstCoordinateGetDto = createCoordinateGetDto(firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        secondCoordinateGetDto = createCoordinateGetDto(secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        // Marker
        firstMarker = createMarker(1L, "London", "London is a busy and beautiful city", LocalDate.now(), 4, Boolean.TRUE, LocalDateTime.now(), null, firstCoordinate);
        secondMarker = createMarker(2L, "New York", "New York is too big city to explore", LocalDate.now(), 5, Boolean.FALSE, LocalDateTime.now(), null, secondCoordinate);
        markers = Arrays.asList(firstMarker, secondMarker);

        // MarkerGetDto
        firstMarkerGetDto = createMarkerGetDto(firstMarker.getId(), firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstCoordinateGetDto);
        secondMarkerGetDto = createMarkerGetDto(secondMarker.getId(), secondMarker.getName(), secondMarker.getDescription(), secondMarker.getEventDate(), secondMarker.getGrade(), secondMarker.getShouldVisitAgain(), secondCoordinateGetDto);
        userDtoMarkers = Arrays.asList(firstMarkerGetDto, secondMarkerGetDto);

        // User
        firstUser = createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968", "tu3ze9ooQu", LocalDateTime.now(), null, userRole);

        // UserPostDto
        userPostDto = createUserPostDto(firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), firstUser.getPassword(), LocalDateTime.now());

        // UserPutDto
        userPutDto = createUserPutDto("Jim", "Gonzalez", "JimBGonzalez@gmail.com", "Cend1997", LocalDateTime.now());

        // UserGetDto
        userGetDto = createUserGetDto(firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), userDtoRoles, null);

        users = Collections.singletonList(firstUser);

        Mockito.when(roleService.findByRoleType(RoleType.USER)).thenReturn(userRole);
        Mockito.when(roleMapper.entitiesToDto(roles)).thenReturn(userDtoRoles);
        Mockito.when(markerMapper.entitiesToDto(markers)).thenReturn(userDtoMarkers);
    }

    @Test
    public void should_Return_User_When_UserPostDto_Correctly_Mapped() {
        final User user = userMapper.dtoToEntity(userPostDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Mone1968", user.getUsername());
    }

    @Test
    public void should_Return_User_When_UserPutDto_Correctly_Mapped() {
        final User user = userMapper.dtoToEntity(firstUser, userPutDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Cend1997", user.getUsername());
    }

    @Test
    public void should_Return_UserGetDto_When_User_Correctly_Mapped() {
        final UserGetDto userGetDto = userMapper.entityToDto(firstUser);

        Assertions.assertNotNull(userGetDto);
        Assertions.assertEquals("Mone1968", userGetDto.getUsername());
    }

    @Test
    public void should_Return_UserGetDto_List_When_Users_Correctly_Mapped() {
        final List<UserGetDto> dtoUsers = userMapper.entitiesToDto(users);

        Assertions.assertNotNull(dtoUsers);
        Assertions.assertEquals(1, dtoUsers.size());
    }

    private Role createRole(final Long id, final RoleType roleType, final String description) {
        final Role role = new Role();
        role.setId(id);
        role.setName(roleType);
        role.setDescription(description);
        return role;
    }

    private RoleGetDto createUserRoleDto(final Role role) {
        RoleGetDto roleGetDto = new RoleGetDto();
        roleGetDto.setName(role.getName().name());
        return roleGetDto;
    }

    private Coordinate createCoordinate(final Long id, final Double longitude, final Double latitude) {
        final Coordinate coordinate = new Coordinate();
        coordinate.setId(id);
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        return coordinate;
    }

    private CoordinateGetDto createCoordinateGetDto(final Double longitude, final Double latitude) {
        final CoordinateGetDto coordinateGetDto = new CoordinateGetDto();
        coordinateGetDto.setLongitude(longitude);
        coordinateGetDto.setLatitude(latitude);
        return coordinateGetDto;
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

    private MarkerGetDto createMarkerGetDto(final Long id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final CoordinateGetDto coordinateGetDto) {
        final MarkerGetDto markerGetDto = new MarkerGetDto();
        markerGetDto.setId(id);
        markerGetDto.setName(name);
        markerGetDto.setDescription(description);
        markerGetDto.setEventDate(eventDate);
        markerGetDto.setGrade(grade);
        markerGetDto.setShouldVisitAgain(shouldVisitAgain);
        markerGetDto.setCoordinate(coordinateGetDto);
        return markerGetDto;
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

    private UserGetDto createUserGetDto(final String firstName, final String lastName, final String email, final String username, final List<RoleGetDto> userDtoRoles, final List<MarkerGetDto> userDtoMarkers) {
        final UserGetDto userGetDto = new UserGetDto();
        userGetDto.setFirstName(firstName);
        userGetDto.setLastName(lastName);
        userGetDto.setEmail(email);
        userGetDto.setUsername(username);
        userGetDto.setRoles(userDtoRoles);
        userGetDto.setMarkers(userDtoMarkers);
        return userGetDto;
    }
}
