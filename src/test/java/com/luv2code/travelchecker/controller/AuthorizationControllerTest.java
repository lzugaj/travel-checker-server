package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.utils.RoleUtil;
import com.luv2code.travelchecker.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    // UserPostDto
    private UserPostDto userPostDto;

    public static final String AUTHORIZE_USER_JSON_CONTENT = "{\n" +
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
        final Role userRole = RoleUtil.createRole(1L, RoleType.USER, "USER role could READ and WRITE data which is assigned only to them");

        // RoleGetDto
        final RoleGetDto userRoleDto = RoleUtil.createRoleGetDto(userRole);
        final List<RoleGetDto> dtoRoles = Collections.singletonList(userRoleDto);

        // User
        final User user = UserUtil.createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968");

        // UserGetDto
        final UserGetDto userGetDto = UserUtil.createUserGetDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), dtoRoles, new ArrayList<>());

        // UserPostDto
        userPostDto = UserUtil.createUserPostDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), LocalDateTime.now());

        BDDMockito.given(modelMapper.map(userPostDto, User.class)).willReturn(user);
        /*BDDMockito.when(modelMapper.map(user, UserGetDto.class)).thenReturn(userGetDto);*/

        BDDMockito.given(userService.save(user)).willReturn(user);
    }

    @Test
    @DisplayName("POST /authorization")
    public void should_Authorize_User() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authorization")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPostDto));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(AUTHORIZE_USER_JSON_CONTENT));
    }
}
