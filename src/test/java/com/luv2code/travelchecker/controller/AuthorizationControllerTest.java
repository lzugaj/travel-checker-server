package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.mapper.UserMapper;
import com.luv2code.travelchecker.service.UserService;
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

    // Role
    private Role userRole;

    // RoleGetDto
    private RoleGetDto userRoleDto;
    private List<RoleGetDto> dtoRoles;

    // User
    private User firstUser;

    // UserGetDto
    private UserGetDto firstUserGetDto;

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
        userRole = createRole(1L, RoleType.USER, "USER role could READ and WRITE data which is assigned only to them");

        // RoleGetDto
        userRoleDto = createRoleGetDto(userRole.getName());

        dtoRoles = Collections.singletonList(userRoleDto);

        // User
        firstUser = createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968", "#tu3ze9ooQu", LocalDateTime.now(), new ArrayList<>(), userRole);

        // UserGetDto
        firstUserGetDto = createUserGetDto(firstUser.getId(), firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), dtoRoles, new ArrayList<>());

        // UserPostDto
        firstUserPostDto = createUserPostDto(firstUser.getFirstName(), firstUser.getLastName(), firstUser.getEmail(), firstUser.getUsername(), firstUser.getPassword());

        BDDMockito.given(userMapper.entityToDto(firstUser)).willReturn(firstUserGetDto);
        BDDMockito.given(userService.save(Mockito.any(UserPostDto.class))).willReturn(firstUser);
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

    private UserPostDto createUserPostDto(final String firstName, final String lastName, final String email, final String username, final String password) {
        return UserPostDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .username(username)
                .password(password)
                .build();
    }

    private Role createRole(final Long id, final RoleType roleType, final String description) {
        final Role role = new Role();
        role.setId(id);
        role.setName(roleType);
        role.setDescription(description);
        return role;
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
        user.setMarkers(markers);
        user.addRole(userRole);
        return user;
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
}
