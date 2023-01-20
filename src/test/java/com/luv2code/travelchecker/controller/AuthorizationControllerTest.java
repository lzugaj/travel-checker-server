package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.mock.RoleMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    private UserGetDto userGetDto;
    private UserPostDto userPostDto;

    @BeforeEach
    public void setup() {
        // Role
        final Role userRole = RoleMock.createRole(UUID.randomUUID(), RoleType.USER, "User role");

        // RoleGetDto
        final RoleGetDto userRoleDto = RoleMock.createRoleGetDto(userRole);
        final List<RoleGetDto> dtoRoles = Collections.singletonList(userRoleDto);

        // User
        final User user = UserMock.createUser(UUID.fromString("bea33870-9542-4d0c-9d8f-5d5b1502d27d"), "John", "Doe", "john.doe@gmail.com", "$2a$12$oF1jOLdVHnoWwEYQwYeJN.icSl.Xs6SKiZ1WZTutygOUrXwh.Mhdu");
        user.setRoles(Collections.singleton(userRole));
        user.setMarkers(null);

        // UserGetDto
        userGetDto = UserMock.createUserGetDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), dtoRoles, new ArrayList<>());

        // UserPostDto
        userPostDto = UserMock.createUserPostDto(user.getFirstName(), user.getLastName(), user.getEmail(), "#Password1234", LocalDateTime.now());

        BDDMockito.given(modelMapper.map(Mockito.any(), Mockito.any()))
                .willReturn(user).willReturn(userGetDto);
        BDDMockito.given(userService.save(user))
                .willReturn(user);
    }

    @Test
    @DisplayName("POST /authorization")
    public void should_Authorize_User() throws Exception {
        final String content = objectMapper.writeValueAsString(userPostDto);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authorization")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(String.valueOf(userGetDto.getId()))))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", CoreMatchers.is(userGetDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", CoreMatchers.is(userGetDto.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("email", CoreMatchers.is(userGetDto.getEmail())));
    }
}
