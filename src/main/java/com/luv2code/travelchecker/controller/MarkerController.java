package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.mapper.MarkerMapper;
import com.luv2code.travelchecker.service.MarkerService;
import com.luv2code.travelchecker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/markers")
public class MarkerController {

    public static final Logger LOGGER = LoggerFactory.getLogger(MarkerController.class);

    private final MarkerService markerService;

    private final UserService userService;

    private final MarkerMapper markerMapper;

    @Autowired
    public MarkerController(final MarkerService markerService,
                            final UserService userService,
                            final MarkerMapper markerMapper) {
        this.markerService = markerService;
        this.userService = userService;
        this.markerMapper = markerMapper;
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<?> save(@PathVariable final String username, @Valid @RequestBody final MarkerPostDto markerPostDto) {
        final User searchedUser = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", searchedUser.getUsername());

        final Marker marker = markerService.save(searchedUser, markerMapper.dtoToEntity(markerPostDto));
        LOGGER.info("Successfully created new Marker with id: ´{}´.", marker.getId());
        return new ResponseEntity<>(markerMapper.entityToDto(marker), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final Marker searchedMarker = markerService.findById(id);
        LOGGER.info("Successfully founded Marker with id: ´{}´.", id);
        return new ResponseEntity<>(markerMapper.entityToDto(searchedMarker), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<Marker> searchedMarkers = markerService.findAll();
        LOGGER.info("Successfully founded ´{}´ Markers.", searchedMarkers.size());
        return new ResponseEntity<>(markerMapper.entitiesToDto(searchedMarkers), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final MarkerPutDto markerPutDto) {
        final Marker searchedMarker = markerService.findById(id);
        LOGGER.info("Successfully founded Marker with id: ´{}´.", id);

        final Marker updatedMarker = markerService.update(markerMapper.dtoToEntity(searchedMarker, markerPutDto));
        LOGGER.info("Successfully updated Marker with id: ´{}´.", id);
        return new ResponseEntity<>(markerMapper.entityToDto(updatedMarker), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final Marker searchedMarker = markerService.findById(id);
        LOGGER.info("Successfully founded Marker with id: ´{}´.", id);

        markerService.delete(searchedMarker);
        LOGGER.info("Successfully deleted Marker with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
