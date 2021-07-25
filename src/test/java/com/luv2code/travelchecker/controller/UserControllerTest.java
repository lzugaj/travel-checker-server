package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.mapper.UserMapper;
import com.luv2code.travelchecker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    // Role
    private Role userRole;

    // RoleGetDto
    private RoleGetDto userRoleDto;
    private List<RoleGetDto> dtoRoles;

    // User
    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private List<User> users;

    // UserGetDto
    private UserGetDto firstUserGetDto;
    private UserGetDto secondUserGetDto;
    private UserGetDto thirdUserGetDto;
    private List<UserGetDto> dtoUsers;

    // UserPutDto
    private UserPutDto firstUserPutDto;

    // Coordinate
    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;

    // CoordinateGetDto
    private CoordinateGetDto firstCoordinateGetDto;
    private CoordinateGetDto secondCoordinateGetDto;

    // Marker
    private Marker firstMarker;
    private Marker secondMarker;
    private List<Marker> markers;

    // MarkerGetDto
    private MarkerGetDto firstMarkerGetDto;
    private MarkerGetDto secondMarkerGetDto;
    private List<MarkerGetDto> dtoMarkers;

    public static final String FIND_USER_JSON = "{\n" +
            "   \"id\":1,\n" +
            "   \"firstName\":\"Eunice\",\n" +
            "   \"lastName\":\"Holt\",\n" +
            "   \"email\":\"eholt@gmail.com\",\n" +
            "   \"username\":\"Mone1968\",\n" +
            "   \"roles\":[\n" +
            "       {\n" +
            "           \"name\":\"USER\"\n" +
            "       }\n" +
            "   ],\n" +
            "   \"markers\":[\n" +
            "       {\n" +
            "           \"id\":1,\n" +
            "           \"name\":\"London\",\n" +
            "           \"description\":\"London is a busy and beautiful city\",\n" +
            "           \"eventDate\":[2021,7,20],\n" +
            "           \"grade\":4,\n" +
            "           \"shouldVisitAgain\":true,\n" +
            "           \"coordinate\":{\n" +
            "               \"longitude\":-0.127758,\n" +
            "               \"latitude\":51.507351\n" +
            "           }\n" +
            "       },\n" +
            "       {\n" +
            "           \"id\":2,\n" +
            "           \"name\":\"New York\",\n" +
            "           \"description\":\"New York is too big city to explore\",\n" +
            "           \"eventDate\":[2021,7,20],\n" +
            "           \"grade\":5,\n" +
            "           \"shouldVisitAgain\":false,\n" +
            "           \"coordinate\":{\n" +
            "               \"longitude\":-74.005974,\n" +
            "               \"latitude\":40.712776\n" +
            "           }\n" +
            "       }\n" +
            "   ]\n" +
            "}";

    public static final String FIND_ALL_USERS_JSON = "[\n" +
            "   {\n" +
            "       \"id\":1,\n" +
            "       \"firstName\":\"Eunice\",\n" +
            "       \"lastName\":\"Holt\",\n" +
            "       \"email\":\"eholt@gmail.com\",\n" +
            "       \"username\":\"Mone1968\",\n" +
            "       \"roles\":[\n" +
            "           {\n" +
            "               \"name\":\"USER\"\n" +
            "           }\n" +
            "       ],\n" +
            "       \"markers\":[\n" +
            "           {\n" +
            "               \"id\":1,\n" +
            "               \"name\":\"London\",\n" +
            "               \"description\":\"London is a busy and beautiful city\",\n" +
            "               \"eventDate\":[2021,7,20],\n" +
            "               \"grade\":4,\n" +
            "               \"shouldVisitAgain\":true,\n" +
            "               \"coordinate\":{\n" +
            "                   \"longitude\":-0.127758,\n" +
            "                   \"latitude\":51.507351\n" +
            "               }\n" +
            "           },\n" +
            "           {\n" +
            "               \"id\":2,\n" +
            "               \"name\":\"New York\",\n" +
            "               \"description\":\"New York is too big city to explore\",\n" +
            "               \"eventDate\":[2021,7,20],\n" +
            "               \"grade\":5,\n" +
            "               \"shouldVisitAgain\":false,\n" +
            "               \"coordinate\":{\n" +
            "                   \"longitude\":-74.005974,\n" +
            "                   \"latitude\":40.712776\n" +
            "               }\n" +
            "           }\n" +
            "       ]\n" +
            "   },\n" +
            "   {\n" +
            "       \"id\":2,\n" +
            "       \"firstName\":\"Sam\",\n" +
            "       \"lastName\":\"Blanco\",\n" +
            "       \"email\":\"samblanco@gmail.com\",\n" +
            "       \"username\":\"Mustrien\",\n" +
            "       \"roles\":[\n" +
            "           {\n" +
            "               \"name\":\"USER\"\n" +
            "           }\n" +
            "       ],\n" +
            "       \"markers\":null\n" +
            "   }\n" +
            "]";

    public final String UPDATE_USER_JSON = "{\n" +
            "   \"id\":1,\n" +
            "   \"firstName\":\"Sarah\",\n" +
            "   \"lastName\":\"Isaac\",\n" +
            "   \"email\":\"SarahLIsaac@gmail.com\",\n" +
            "   \"username\":\"Mostion123\",\n" +
            "   \"roles\":[\n" +
            "       {\n" +
            "           \"name\":\"USER\"\n" +
            "       }\n" +
            "   ],\n" +
            "   \"markers\":[\n" +
            "       {\n" +
            "           \"id\":1,\n" +
            "           \"name\":\"London\",\n" +
            "           \"description\":\"London is a busy and beautiful city\",\n" +
            "           \"eventDate\":[2021,7,20],\n" +
            "           \"grade\":4,\n" +
            "           \"shouldVisitAgain\":true,\n" +
            "           \"coordinate\":{\n" +
            "               \"longitude\":-0.127758,\n" +
            "               \"latitude\":51.507351\n" +
            "           }\n" +
            "       },\n" +
            "       {\n" +
            "           \"id\":2,\n" +
            "           \"name\":\"New York\",\n" +
            "           \"description\":\"New York is too big city to explore\",\n" +
            "           \"eventDate\":[2021,7,20],\n" +
            "           \"grade\":5,\n" +
            "           \"shouldVisitAgain\":false,\n" +
            "           \"coordinate\":{\n" +
            "               \"longitude\":-74.005974,\n" +
            "               \"latitude\":40.712776\n" +
            "           }\n" +
            "       }\n" +
            "   ]\n" +
            "}";

    @BeforeEach
    public void setup() {
        // Role
        userRole = createRole(1L, RoleType.USER, "USER role could READ and WRITE data which is assigned only to them");

        // RoleGetDto
        userRoleDto = createRoleGetDto(userRole.getName());

        dtoRoles = Collections.singletonList(userRoleDto);

        // Coordinate
        firstCoordinate = createCoordinate(1L, -0.127758, 51.507351);
        secondCoordinate = createCoordinate(2L, -74.005974, 40.712776);

        // Marker
        firstMarker = createMarker(1L, "London", "London is a busy and beautiful city", LocalDate.of(2021, 7, 20), 4, Boolean.TRUE, LocalDateTime.now(), null, firstCoordinate);
        secondMarker = createMarker(2L, "New York", "New York is too big city to explore", LocalDate.of(2021, 7, 20), 5, Boolean.FALSE, LocalDateTime.now(), null, secondCoordinate);

        markers = Arrays.asList(firstMarker, secondMarker);

        // CoordinateGetDto
        firstCoordinateGetDto = createCoordinateGetDto(firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        secondCoordinateGetDto = createCoordinateGetDto(secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        // MarkerGetDto
        firstMarkerGetDto = createMarkerGetDto(firstMarker.getId(), firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstCoordinateGetDto);
        secondMarkerGetDto = createMarkerGetDto(secondMarker.getId(), secondMarker.getName(), secondMarker.getDescription(), secondMarker.getEventDate(), secondMarker.getGrade(), secondMarker.getShouldVisitAgain(), secondCoordinateGetDto);

        dtoMarkers = Arrays.asList(firstMarkerGetDto, secondMarkerGetDto);

        // User
        firstUser = createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968", "tu3ze9ooQu", LocalDateTime.now(), markers, userRole);
        secondUser = createUser(2L, "Sam", "Blanco", "samblanco@gmail.com", "Mustrien", "goh7DuoF5", LocalDateTime.now(), new ArrayList<>(), userRole);
        thirdUser = createUser(1L, "Sarah", "Isaac", "SarahLIsaac@gmail.com", "Mostion",  firstUser.getPassword(), firstUser.getCreatedAt(), firstUser.getMarkers(), userRole);

        users = Arrays.asList(firstUser, secondUser);

        // UserGetDto
        firstUserGetDto = createUserGetDto(firstUser.getId(), firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), dtoRoles, dtoMarkers);
        secondUserGetDto = createUserGetDto(secondUser.getId(), secondUser.getFirstName(), secondUser.getLastName(), secondUser.getEmail(), secondUser.getUsername(), dtoRoles, new ArrayList<>());
        thirdUserGetDto = createUserGetDto(thirdUser.getId(), thirdUser.getFirstName(), thirdUser.getLastName(), thirdUser.getEmail(), thirdUser.getUsername(), dtoRoles, dtoMarkers);

        dtoUsers = Arrays.asList(firstUserGetDto, secondUserGetDto);

        // UserPutDto
        firstUserPutDto = createUserPutDto("Sarah", "Isaac", "SarahLIsaac@gmail.com", "Mostion123");

        Mockito.when(userMapper.entityToDto(Mockito.any(User.class))).thenReturn(firstUserGetDto);
        Mockito.when(userMapper.entitiesToDto(users)).thenReturn(dtoUsers);

        Mockito.when(userService.findById(firstUser.getId())).thenReturn(firstUser);
        Mockito.when(userService.findByUsername(firstUser.getUsername())).thenReturn(firstUser);
        Mockito.when(userService.findAll()).thenReturn(users);
        Mockito.when(userService.update(Mockito.any(User.class), Mockito.any(UserPutDto.class))).thenReturn(thirdUser);
    }

    @Test
    @DisplayName("GET /users/1")
    public void should_Find_User_By_Id() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/" + firstUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(FIND_USER_JSON));
    }

    @Test
    @DisplayName("GET /users/username/Mone1968")
    public void should_Find_User_By_Username() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/username/" + firstUser.getUsername()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(FIND_USER_JSON));
    }

    @Test
    @DisplayName("GET /users")
    public void should_Find_All_Users() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(FIND_ALL_USERS_JSON));
    }

    @Test
    @DisplayName("PUT /users/username/Mone1968")
    public void should_Update_User() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/username/" + firstUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstUserPutDto));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(UPDATE_USER_JSON));
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

    private UserGetDto createUserGetDto(final Long id, final String firstName, final String lastName, final String email, final String username, final List<RoleGetDto> dtoRoles, final List<MarkerGetDto> dtoMarkers) {
        return UserGetDto.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .username(username)
                .roles(dtoRoles)
                .markers(dtoMarkers)
                .build();
    }

    private MarkerGetDto createMarkerGetDto(final Long id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final CoordinateGetDto coordinateGetDto) {
        return MarkerGetDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .eventDate(eventDate)
                .grade(grade)
                .shouldVisitAgain(shouldVisitAgain)
                .coordinate(coordinateGetDto)
                .build();
    }

    private CoordinateGetDto createCoordinateGetDto(final Double longitude, final Double latitude) {
        return CoordinateGetDto.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }

    private RoleGetDto createRoleGetDto(final RoleType roleType) {
        return RoleGetDto.builder()
                .name(roleType.name())
                .build();
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
//        user.setProfileImage(null);
        user.setMarkers(markers);
        user.addRole(userRole);
        return user;
    }

    private UserPutDto createUserPutDto(final String firstName, final String lastName, final String email, final String username) {
        return UserPutDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .username(username)
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
