package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.mock.JwtTokenMock;
import com.luv2code.travelchecker.mock.RoleMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.AuthenticationService;
import com.luv2code.travelchecker.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticatedUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private ModelMapper modelMapper;

    private User me;

    private static final String JWT_TOKEN = JwtTokenMock.createUserToken("john.doe@gmail.com");

    @BeforeEach
    public void setup() {
        // Role
        final Role userRole = RoleMock.createRole(UUID.randomUUID(), RoleType.USER, "User role");

        // RoleGetDto
        final RoleGetDto userRoleDto = RoleMock.createRoleGetDto(userRole);
        final List<RoleGetDto> rolesDto = List.of(userRoleDto);

        // User
        me = UserMock.createUser(UUID.fromString("bea33870-9542-4d0c-9d8f-5d5b1502d27d"), "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        me.setRoles(Collections.singleton(userRole));
        me.setMarkers(null);

        // UserGetDto
        final UserGetDto meDto = UserMock.createUserGetDto(me.getId(), me.getFirstName(), me.getLastName(), me.getEmail(), rolesDto, null);

        BDDMockito.given(userRepository.findByEmail(me.getEmail())).willReturn(Optional.of(me));
        BDDMockito.given(authenticationService.getAuthenticatedEmail()).willReturn(me.getEmail());
        BDDMockito.given(userService.findByEmail(me.getEmail())).willReturn(me);
        BDDMockito.given(modelMapper.map(me, UserGetDto.class)).willReturn(meDto);
    }

    @Test
    @DisplayName("GET 200 /auth/me")
    public void should_Get_My_Auth_Details() throws Exception {
        final String content = objectMapper.writeValueAsString(me);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/auth/me")
                .header(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(String.valueOf(me.getId()))))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", CoreMatchers.is(me.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", CoreMatchers.is(me.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("email", CoreMatchers.is(me.getEmail())));
    }
}
