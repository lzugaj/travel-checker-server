package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.service.MapboxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(MapboxController.class)
public class MapboxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MapboxService mapboxService;

    private static final String TOKEN = "dsapodpmm32k1mpofjjfmpo213po21";

    @BeforeEach
    public void setup() {
        BDDMockito.given(mapboxService.fetchToken()).willReturn(TOKEN);
    }

    @Test
    public void should_Fetch_Mapbox_Token() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/mapbox/token"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(TOKEN));
    }
}
