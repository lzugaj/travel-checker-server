package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.mock.*;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.AuthenticationService;
import com.luv2code.travelchecker.service.MarkerService;
import com.luv2code.travelchecker.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MarkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MarkerService markerService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private ModelMapper modelMapper;

    private static final String JWT_TOKEN = JwtTokenMock.createUserToken("john.doe@gmail.com");

    private MarkerGetDto firstMarkerGetDto;

    @BeforeEach
    public void setup() {
        // CoordinateGetDto
        final CoordinateGetDto firstCoordinateGetDto = CoordinateMock.createCoordinateGetDto(UUID.randomUUID(), 15.966568111, 45.815399222);

        // Coordinate
        final Coordinate firstCoordinate = CoordinateMock.createCoordinate(firstCoordinateGetDto.getLongitude(), firstCoordinateGetDto.getLatitude());

        // MarkerGetDto
        firstMarkerGetDto = MarkerMock.createMarkerGetDto(UUID.fromString("f5262029-0c6d-453a-96ee-d6dd1b36bd0e"), "Zagreb", "Zagreb was beautiful destination", LocalDate.of(2021, 3, 11), 4, Boolean.TRUE, firstCoordinateGetDto);

        // Marker
        final Marker firstMarker = MarkerMock.createMarker(firstMarkerGetDto.getId(), firstMarkerGetDto.getName(), firstMarkerGetDto.getDescription(), firstMarkerGetDto.getEventDate(), firstMarkerGetDto.getGrade(), firstMarkerGetDto.getShouldVisitAgain(), LocalDateTime.now(), firstCoordinate);

        // Role
        final Role userRole = RoleMock.createRole(UUID.randomUUID(), RoleType.USER, "User role");

        // User
        final User user = UserMock.createUser(UUID.fromString("bea33870-9542-4d0c-9d8f-5d5b1502d27d"), "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        user.setRoles(Collections.singleton(userRole));
        user.setMarkers(null);

        BDDMockito.given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        BDDMockito.given(markerService.findById(firstCoordinateGetDto.getId())).willReturn(firstMarker);
        BDDMockito.given(modelMapper.map(Mockito.any(), Mockito.any())).willReturn(firstMarkerGetDto);
    }

    @Test
    public void should_Return_Searched_Marker() throws Exception {
        final String content = objectMapper.writeValueAsString(firstMarkerGetDto);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/markers/" + firstMarkerGetDto.getId())
                .header(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(String.valueOf(firstMarkerGetDto.getId()))))
                .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(firstMarkerGetDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("description", CoreMatchers.is(firstMarkerGetDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("grade", CoreMatchers.is(firstMarkerGetDto.getGrade())))
                .andExpect(MockMvcResultMatchers.jsonPath("eventDate", CoreMatchers.is(String.valueOf(firstMarkerGetDto.getEventDate()))))
                .andExpect(MockMvcResultMatchers.jsonPath("shouldVisitAgain", CoreMatchers.is(firstMarkerGetDto.getShouldVisitAgain())));
    }
}
