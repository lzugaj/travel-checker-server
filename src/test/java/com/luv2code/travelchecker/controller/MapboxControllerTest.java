package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.service.MapboxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MapboxController.class)
public class MapboxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MapboxService mapboxService;

    private static final String TOKEN = "dsapodpmm32k1mpofjjfmpo213po21";

    private static final String FETCH_TOKEN_JSON = "{\n" +
            "   \"mapboxToken\":\"dsapodpmm32k1mpofjjfmpo213po21\"" +
            "}";

    @BeforeEach
    public void setup() {
        BDDMockito.given(mapboxService.getToken()).willReturn(TOKEN);
    }

    @Test
    @DisplayName("GET /mapbox/token")
    public void should_Fetch_Mapbox_Token() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mapbox/token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TOKEN));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(FETCH_TOKEN_JSON));
    }
}
