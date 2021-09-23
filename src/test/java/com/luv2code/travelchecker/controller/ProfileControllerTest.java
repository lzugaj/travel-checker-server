package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.luv2code.travelchecker.util.SecurityConstants.HEADER_NAME;
import static com.luv2code.travelchecker.util.SecurityConstants.TOKEN_PREFIX;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    private UserPutDto userPutDto;

    private static final String JWT_TOKEN = JwtTokenUtil.createUserToken("john.doe@gmail.com");

    @BeforeEach
    public void setup() {
        final Role userRole = RoleUtil.createRole(1L, RoleType.USER, "User role");

        final RoleGetDto roleGetDto = RoleUtil.createRoleGetDto(userRole);
        final List<RoleGetDto> rolesGetDto = List.of(roleGetDto);

        final User user = UserUtil.createUser(1L, "John", "Doe", "john.doe@gmail.com", "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6");
        user.setRoles(Collections.singleton(userRole));

        userPutDto = UserUtil.createUserPutDto(user.getFirstName(), user.getLastName(), user.getEmail(), LocalDateTime.now());
        userPutDto.setId(user.getId());

        final UserGetDto userGetDto = UserUtil.createUserGetDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), rolesGetDto, null);

        BDDMockito.given(modelMapper.map(userPutDto, User.class)).willReturn(user);
        BDDMockito.given(modelMapper.map(user, UserGetDto.class)).willReturn(userGetDto);

        BDDMockito.given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        BDDMockito.given(authenticationService.getAuthenticatedEmail()).willReturn(user.getEmail());
        BDDMockito.given(userService.findByEmail(user.getEmail())).willReturn(user);
        BDDMockito.given(userService.update(user.getEmail(), user)).willReturn(user);
    }

    @Test
    @DisplayName("PUT /profiles/me")
    public void should_Update_My_Profile() throws Exception {
        final String content = objectMapper.writeValueAsString(userPutDto);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/profiles/me")
                .header(HEADER_NAME, TOKEN_PREFIX + JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", CoreMatchers.is(userPutDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", CoreMatchers.is(userPutDto.getLastName())));
    }
}
