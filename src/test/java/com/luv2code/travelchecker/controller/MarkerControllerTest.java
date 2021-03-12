package com.luv2code.travelchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.service.MarkerService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(MarkerController.class)
public class MarkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarkerService markerService;

    private ObjectMapper objectMapper;

    private Marker firstMarker;
    private Marker secondMarker;
    private Marker thirdMarker;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        final Coordinate zagrebCoordinate = new Coordinate();
        zagrebCoordinate.setId(1L);
        zagrebCoordinate.setLongitude(15.966568111);
        zagrebCoordinate.setLatitude(45.815399222);

        final Coordinate splitCoordinate = new Coordinate();
        splitCoordinate.setId(2L);
        splitCoordinate.setLongitude(15.242222412);
        splitCoordinate.setLatitude(44.119722345);

        final Coordinate dubrovnikCoordinate = new Coordinate();
        dubrovnikCoordinate.setId(3L);
        dubrovnikCoordinate.setLongitude(18.092162345);
        dubrovnikCoordinate.setLatitude(42.648071234);

        firstMarker = new Marker();
        firstMarker.setId(1L);
        firstMarker.setName("Zagreb");
        firstMarker.setDescription("Zagreb was beautiful destination.");
        firstMarker.setEventDate(LocalDate.of(2021, 3, 11));
        firstMarker.setGrade(4);
        firstMarker.setShouldVisitAgain(true);
        firstMarker.setCoordinate(zagrebCoordinate);

        secondMarker = new Marker();
        secondMarker.setId(2L);
        secondMarker.setName("Split");
        secondMarker.setDescription("Split is nice but there is too much tourists.");
        secondMarker.setEventDate(LocalDate.of(2020, 8, 1));
        secondMarker.setGrade(3);
        secondMarker.setShouldVisitAgain(false);
        secondMarker.setCoordinate(splitCoordinate);

        thirdMarker = new Marker();
        thirdMarker.setId(3L);
        thirdMarker.setName("Dubrovnik");
        thirdMarker.setDescription("Dubrovnik is like a paradise.");
        thirdMarker.setEventDate(LocalDate.of(2020, 10, 15));
        thirdMarker.setGrade(5);
        thirdMarker.setShouldVisitAgain(true);
        thirdMarker.setCoordinate(dubrovnikCoordinate);

        final List<Marker> markers = new ArrayList<>();
        markers.add(secondMarker);
        markers.add(thirdMarker);

        BDDMockito.given(markerService.save(firstMarker)).willReturn(firstMarker);
        BDDMockito.given(markerService.findById(secondMarker.getId())).willReturn(secondMarker);
        BDDMockito.given(markerService.findAll()).willReturn(markers);
    }

        @Test
        public void should_Create_Marker_When_Form_Is_Valid() throws Exception {
            this.mockMvc
                    .perform(MockMvcRequestBuilders.post("/markers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(firstMarker)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
        }

        @Test
        public void should_Return_Marker_When_Id_Is_Valid() throws Exception {
            this.mockMvc
                    .perform(MockMvcRequestBuilders.get("/markers/{id}", secondMarker.getId()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("id").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(2)));
        }

        @Test
        public void should_Return_All_Markers() throws Exception {
            this.mockMvc
                    .perform(MockMvcRequestBuilders.get("/markers"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)));
    }
}
