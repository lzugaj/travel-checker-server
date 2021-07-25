package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.dto.mapbox.MapboxGetDto;
import com.luv2code.travelchecker.service.MapboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mapbox")
public class MapboxController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapboxController.class);

    private final MapboxService mapboxService;

    @Autowired
    public MapboxController(final MapboxService mapboxService) {
        this.mapboxService = mapboxService;
    }

    @GetMapping("/token")
    public ResponseEntity<?> fetchToken() {
        final String mapboxToken = mapboxService.getToken();
        LOGGER.info("Successfully founded Mapbox token.");
        return new ResponseEntity<>(new MapboxGetDto(mapboxToken), HttpStatus.OK);
    }
}
