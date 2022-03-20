package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.mock.JwtTokenMock;
import com.luv2code.travelchecker.mock.RoleMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.MapboxService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class MapboxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MapboxService mapboxService;

    @MockBean
    private UserRepository userRepository;

    private static final String MAPBOX_TOKEN = "dsapodpmm32k1mpofjjfmpo213po21";
    private static final String JWT_TOKEN = JwtTokenMock.createUserToken("john.doe@gmail.com");

    @BeforeEach
    public void setup() {
        final Role userRole = RoleMock.createRole(UUID.randomUUID(), RoleType.USER, "User role");

        final User user = UserMock.createUser(UUID.randomUUID(), "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        user.setRoles(Collections.singleton(userRole));

        BDDMockito.given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        BDDMockito.given(mapboxService.getToken()).willReturn(MAPBOX_TOKEN);
    }

    @Test
    @DisplayName("GET 200 /mapbox/token")
    public void should_Fetch_Mapbox_Token() throws Exception {
        final String content = objectMapper.writeValueAsString(MAPBOX_TOKEN);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mapbox/token")
                .header(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("mapboxToken", CoreMatchers.is(MAPBOX_TOKEN)));

    }
}
