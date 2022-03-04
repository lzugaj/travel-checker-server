/*
package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.RoleUtil;
import com.luv2code.travelchecker.util.UserUtil;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
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
        final Role userRole = RoleUtil.createRole(1L, RoleType.USER, "User role");

        // RoleGetDto
        final RoleGetDto userRoleDto = RoleUtil.createRoleGetDto(userRole);
        final List<RoleGetDto> dtoRoles = Collections.singletonList(userRoleDto);

        // User
        final User user = UserUtil.createUser(1L, "John", "Doe", "john.doe@gmail.com", "$2a$12$oF1jOLdVHnoWwEYQwYeJN.icSl.Xs6SKiZ1WZTutygOUrXwh.Mhdu");
        user.setRoles(Collections.singleton(userRole));
        user.setMarkers(null);

        // UserGetDto
        userGetDto = UserUtil.createUserGetDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), dtoRoles, new ArrayList<>());

        // UserPostDto
        userPostDto = UserUtil.createUserPostDto(user.getFirstName(), user.getLastName(), user.getEmail(), "#Password1234", LocalDateTime.now());

        BDDMockito.given(modelMapper.map(Mockito.any(), Mockito.any())).willReturn(user).willReturn(userGetDto);
        BDDMockito.given(userService.save(user)).willReturn(user);
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
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", CoreMatchers.is(userGetDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", CoreMatchers.is(userGetDto.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("email", CoreMatchers.is(userGetDto.getEmail())));
    }
}
*/
