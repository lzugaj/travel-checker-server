package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.repository.UserRepository;
import com.luv2code.travelchecker.service.AuthenticationService;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.JwtTokenUtil;
import com.luv2code.travelchecker.util.RoleUtil;
import com.luv2code.travelchecker.util.UserUtil;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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

import java.util.Collections;
import java.util.List;

import static com.luv2code.travelchecker.util.SecurityConstants.HEADER_NAME;
import static com.luv2code.travelchecker.util.SecurityConstants.TOKEN_PREFIX;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AuthenticatedUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private ModelMapper modelMapper;

    private User me;

    private static final String USER_JWT_TOKEN = JwtTokenUtil.createUserToken("john.doe@gmail.com");

    @BeforeEach
    public void setup() {
        // Role
        final Role userRole = RoleUtil.createRole(1L, RoleType.USER, "User role");

        // RoleGetDto
        final RoleGetDto userRoleDto = RoleUtil.createRoleGetDto(userRole);
        final List<RoleGetDto> rolesDto = List.of(userRoleDto);

        // User
        me = UserUtil.createUser(1L, "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        me.setRoles(Collections.singleton(userRole));
        me.setMarkers(null);

        // UserGetDto
        final UserGetDto meDto = UserUtil.createUserGetDto(me.getId(), me.getFirstName(), me.getLastName(), me.getEmail(), rolesDto, null);

        BDDMockito.given(userRepository.findByEmail(me.getEmail())).willReturn(java.util.Optional.of(me));
        BDDMockito.given(authenticationService.getAuthenticatedEmail()).willReturn(me.getEmail());
        BDDMockito.given(userService.findByEmail(me.getEmail())).willReturn(me);
        BDDMockito.given(modelMapper.map(me, UserGetDto.class)).willReturn(meDto);
    }

    @Test
    @DisplayName("GET /auth/me")
    public void should_Get_My_Auth_Details() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/auth/me")
                .header(HEADER_NAME, TOKEN_PREFIX + USER_JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", CoreMatchers.is(me.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", CoreMatchers.is(me.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("email", CoreMatchers.is(me.getEmail())));
    }
}
