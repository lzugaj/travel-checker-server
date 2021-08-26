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
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.utils.CoordinateUtil;
import com.luv2code.travelchecker.utils.MarkerUtil;
import com.luv2code.travelchecker.utils.RoleUtil;
import com.luv2code.travelchecker.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    // User
    private User firstUser;
    private User thirdUser;

    // UserPutDto
    private UserPutDto firstUserPutDto;

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
            "           \"eventDate\":\"2021-07-20\",\n" +
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
            "           \"eventDate\":\"2021-07-20\",\n" +
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
            "               \"eventDate\":\"2021-07-20\",\n" +
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
            "               \"eventDate\":\"2021-07-20\",\n" +
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
            "       \"markers\":[]\n" +
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
            "           \"eventDate\":\"2021-07-20\",\n" +
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
            "           \"eventDate\":\"2021-07-20\",\n" +
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
        Role userRole = RoleUtil.createRole(1L, RoleType.USER, "USER role could READ and WRITE data which is assigned only to them");

        // RoleGetDto
        RoleGetDto userRoleDto = RoleUtil.createRoleGetDto(userRole);

        List<RoleGetDto> dtoRoles = Collections.singletonList(userRoleDto);

        // Coordinate
        Coordinate firstCoordinate = CoordinateUtil.createCoordinate(1L, -0.127758, 51.507351);
        Coordinate secondCoordinate = CoordinateUtil.createCoordinate(2L, -74.005974, 40.712776);

        // Marker
        Marker firstMarker = MarkerUtil.createMarker(1L, "London", "London is a busy and beautiful city", LocalDate.of(2021, 7, 20), 4, Boolean.TRUE, LocalDateTime.now(), firstCoordinate);
        Marker secondMarker = MarkerUtil.createMarker(2L, "New York", "New York is too big city to explore", LocalDate.of(2021, 7, 20), 5, Boolean.FALSE, LocalDateTime.now(), secondCoordinate);

        // CoordinateGetDto
        CoordinateGetDto firstCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(firstCoordinate.getId(), firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        CoordinateGetDto secondCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(secondCoordinate.getId(), secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        // MarkerGetDto
        MarkerGetDto firstMarkerGetDto = MarkerUtil.createMarkerGetDto(firstMarker.getId(), firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstCoordinateGetDto);
        MarkerGetDto secondMarkerGetDto = MarkerUtil.createMarkerGetDto(secondMarker.getId(), secondMarker.getName(), secondMarker.getDescription(), secondMarker.getEventDate(), secondMarker.getGrade(), secondMarker.getShouldVisitAgain(), secondCoordinateGetDto);

        List<MarkerGetDto> dtoMarkers = Arrays.asList(firstMarkerGetDto, secondMarkerGetDto);

        // User
        firstUser = UserUtil.createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968", "tu3ze9ooQu");
        User secondUser = UserUtil.createUser(2L, "Sam", "Blanco", "samblanco@gmail.com", "Mustrien", "goh7DuoF5");
        thirdUser = UserUtil.createUser(1L, "Sarah", "Isaac", "SarahLIsaac@gmail.com", "Mostion123", "goh7DuoF5");

        List<User> users = Arrays.asList(firstUser, secondUser);

        // UserGetDto
        UserGetDto firstUserGetDto = UserUtil.createUserGetDto(firstUser.getId(), firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), dtoRoles, dtoMarkers);
        UserGetDto secondUserGetDto = UserUtil.createUserGetDto(secondUser.getId(), secondUser.getFirstName(), secondUser.getLastName(), secondUser.getEmail(), secondUser.getUsername(), dtoRoles, new ArrayList<>());
        UserGetDto thirdUserGetDto = UserUtil.createUserGetDto(thirdUser.getId(), thirdUser.getFirstName(), thirdUser.getLastName(), thirdUser.getEmail(), thirdUser.getUsername(), dtoRoles, dtoMarkers);

        // UserPutDto
        firstUserPutDto = UserUtil.createUserPutDto(thirdUser.getFirstName(), thirdUser.getLastName(), thirdUser.getEmail(), thirdUser.getUsername(), LocalDateTime.now());

        Mockito.when(userService.findById(firstUser.getId())).thenReturn(firstUser);
        Mockito.when(userService.findByUsername(firstUser.getUsername())).thenReturn(firstUser);
        Mockito.when(userService.findAll()).thenReturn(users);
        Mockito.when(userService.update(firstUser.getUsername(), firstUser)).thenReturn(thirdUser);
    }

    @Test
    @DisplayName("GET /users/1")
    public void should_Find_User_By_Id() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/" + firstUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(FIND_USER_JSON));
    }

    @Test
    @DisplayName("GET /users/username/Mone1968")
    public void should_Find_User_By_Username() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/username/" + firstUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(FIND_USER_JSON));
    }

    @Test
    @DisplayName("GET /users")
    public void should_Find_All_Users() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc
                .perform(requestBuilder)
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
}
