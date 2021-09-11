package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.luv2code.travelchecker.util.SecurityConstants.HEADER_NAME;
import static com.luv2code.travelchecker.util.SecurityConstants.TOKEN_PREFIX;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    // User
    private User firstUser;

    private static final String ADMIN_JWT_TOKEN = JwtTokenUtil.createAdminToken("admin@gmail.com");

    @BeforeEach
    public void setup() {
        // Role
        final Role adminRole = RoleUtil.createRole(1L, RoleType.ADMIN, "Admin role");
        final Role userRole = RoleUtil.createRole(2L, RoleType.USER, "User role");

        // RoleGetDto
        final RoleGetDto userRoleDto = RoleUtil.createRoleGetDto(userRole);
        final List<RoleGetDto> dtoRoles = List.of(userRoleDto);

        // Coordinate
        final Coordinate firstCoordinate = CoordinateUtil.createCoordinate(1L, -0.127758, 51.507351);
        final Coordinate secondCoordinate = CoordinateUtil.createCoordinate(2L, -74.005974, 40.712776);

        // Marker
        final Marker firstMarker = MarkerUtil.createMarker(1L, "London", "London is a busy and beautiful city", LocalDate.of(2021, 7, 20), 4, Boolean.TRUE, LocalDateTime.now(), firstCoordinate);
        final Marker secondMarker = MarkerUtil.createMarker(2L, "New York", "New York is too big city to explore", LocalDate.of(2021, 7, 20), 5, Boolean.FALSE, LocalDateTime.now(), secondCoordinate);

        // CoordinateGetDto
        final CoordinateGetDto firstCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(firstCoordinate.getId(), firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        final CoordinateGetDto secondCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(secondCoordinate.getId(), secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        // MarkerGetDto
        final MarkerGetDto firstMarkerGetDto = MarkerUtil.createMarkerGetDto(firstMarker.getId(), firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstCoordinateGetDto);
        final MarkerGetDto secondMarkerGetDto = MarkerUtil.createMarkerGetDto(secondMarker.getId(), secondMarker.getName(), secondMarker.getDescription(), secondMarker.getEventDate(), secondMarker.getGrade(), secondMarker.getShouldVisitAgain(), secondCoordinateGetDto);
        final List<MarkerGetDto> dtoMarkers = Arrays.asList(firstMarkerGetDto, secondMarkerGetDto);

        // User
        final User admin = UserUtil.createUser(3L, "Admin", "Administrator", "admin@gmail.com", "$2a$12$WBG7PQLSfumuAHH0vUlkbuuHKLRrhYeLJ1d3FIvitkFKvuLuGX47u");
        admin.setRoles(Collections.singleton(adminRole));

        firstUser = UserUtil.createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "$2a$12$fxoy9xif4UoE1650oX3oAu1piTM6JkJ38DD5ZjtyA1Zw7hjLw0j8y");
        final User secondUser = UserUtil.createUser(2L, "Sam", "Blanco", "samblanco@gmail.com", "$2a$12$/8ROxRmTtxHUa/fSLNaIR.qO6cE0BYtInuLkYGm37wi5fOJgV9xl.");
        final User thirdUser = UserUtil.createUser(1L, "Sarah", "Isaac", "SarahLIsaac@gmail.com", "$2a$12$fxoy9xif4UoE1650oX3oAu1piTM6JkJ38DD5ZjtyA1Zw7hjLw0j8y");

        firstUser.setRoles(Collections.singleton(userRole));
        secondUser.setRoles(Collections.singleton(userRole));
        thirdUser.setRoles(Collections.singleton(userRole));

        final List<User> users = Arrays.asList(firstUser, secondUser);

        // UserGetDto
        final UserGetDto firstUserGetDto = UserUtil.createUserGetDto(firstUser.getId(), firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), dtoRoles, dtoMarkers);
        final UserGetDto secondUserGetDto = UserUtil.createUserGetDto(secondUser.getId(), secondUser.getFirstName(), secondUser.getLastName(), secondUser.getEmail(), dtoRoles, new ArrayList<>());
        // final UserGetDto thirdUserGetDto = UserUtil.createUserGetDto(thirdUser.getId(), thirdUser.getFirstName(), thirdUser.getLastName(), thirdUser.getEmail(), dtoRoles, new ArrayList<>());

        BDDMockito.given(userService.findByEmail(admin.getEmail())).willReturn(admin);
        BDDMockito.given(userRepository.findByEmail(admin.getEmail())).willReturn(Optional.of(admin));

        BDDMockito.given(userService.findById(firstUser.getId())).willReturn(firstUser);
        BDDMockito.given(modelMapper.map(Mockito.any(), Mockito.any())).willReturn(firstUserGetDto, secondUserGetDto);

        BDDMockito.given(userService.findAll()).willReturn(users);
    }

    @Test
    @DisplayName("GET /users/1")
    public void should_Find_User_By_Id() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/" + firstUser.getId())
                .header(HEADER_NAME, TOKEN_PREFIX + ADMIN_JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", CoreMatchers.is(firstUser.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", CoreMatchers.is(firstUser.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("email", CoreMatchers.is(firstUser.getEmail())));
    }

    @Test
    @DisplayName("GET /users")
    public void should_Find_All_Users() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .header(HEADER_NAME, TOKEN_PREFIX + ADMIN_JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
