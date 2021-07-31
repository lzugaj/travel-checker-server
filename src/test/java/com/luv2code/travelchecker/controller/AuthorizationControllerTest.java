package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.mapper.UserMapper;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.utils.RoleUtil;
import com.luv2code.travelchecker.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AuthorizationController.class)
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    // UserPostDto
    private UserPostDto firstUserPostDto;

    public static final String AUTHORIZE_USER_JSON = "{\n" +
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
            "   \"markers\":[]\n" +
            "}";

    @BeforeEach
    public void setup() {
        // Role
        Role userRole = RoleUtil.createRole(1L, RoleType.USER, "USER role could READ and WRITE data which is assigned only to them");

        // RoleGetDto
        RoleGetDto userRoleDto = RoleUtil.createRoleGetDto(userRole);

        List<RoleGetDto> dtoRoles = Collections.singletonList(userRoleDto);

        // User
        User firstUser = UserUtil.createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968", "#tu3ze9ooQu");

        // UserGetDto
        UserGetDto firstUserGetDto = UserUtil.createUserGetDto(firstUser.getId(), firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), dtoRoles, new ArrayList<>());

        // UserPostDto
        firstUserPostDto = UserUtil.createUserPostDto(firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), firstUser.getPassword(), LocalDateTime.now());

        BDDMockito.given(userMapper.dtoToEntity(Mockito.any(UserPostDto.class))).willReturn(firstUser);
        BDDMockito.given(userMapper.entityToDto(firstUser)).willReturn(firstUserGetDto);

        BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(firstUser);
    }

    @Test
    @DisplayName("POST /authorization")
    public void should_Authorize_User() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authorization")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstUserPostDto));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(AUTHORIZE_USER_JSON));
    }
}
