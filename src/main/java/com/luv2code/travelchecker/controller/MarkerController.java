package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.service.MarkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/markers")
public class MarkerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerController.class);

    private final MarkerService markerService;

    @Autowired
    public MarkerController(final MarkerService markerService) {
        this.markerService = markerService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody final Marker marker) {
        final Marker newMarker = markerService.save(marker);
        LOGGER.info("Successfully created new Marker with id: ´{}´.", newMarker.getId());
        return new ResponseEntity<>(newMarker, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findById(@PathVariable final Long id) {
        final Marker marker = markerService.findById(id);
        LOGGER.info("Successfully founded Marker with id: ´{}´.", id);
        return new ResponseEntity<>(marker, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<Marker> markers = markerService.findAll();
        LOGGER.info("Successfully founded ´{}´ Markers.", markers.size());
        return new ResponseEntity<>(markers, HttpStatus.OK);
    }
}
