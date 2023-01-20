package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.dto.password.ForgetPasswordDto;
import com.luv2code.travelchecker.mock.ForgetPasswordMock;
import com.luv2code.travelchecker.service.ForgetPasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
@AutoConfigureMockMvc
public class ForgetPasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ForgetPasswordService forgetPasswordService;

    @MockBean
    private ModelMapper modelMapper;

    private ForgetPasswordDto forgetPasswordDto;

    @BeforeEach
    public void setup() {
        forgetPasswordDto = ForgetPasswordMock.forgetPasswordDto("john.doe@gmail.com");
    }

    @Test
    @DisplayName("POST /forget-password")
    public void should_Successfully_Request_Password_Reset() throws Exception {
        /*Mockito.verify(forgetPasswordService, Mockito.times(1))
                .requestPasswordReset(forgetPasswordDto);*/

        final String content = objectMapper.writeValueAsString(forgetPasswordDto);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/forget-password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
