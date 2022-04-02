package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.service.AuthenticationService;
import com.luv2code.travelchecker.service.MarkerService;
import com.luv2code.travelchecker.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/markers")
public class MarkerController {

    public static final Logger LOGGER = LoggerFactory.getLogger(MarkerController.class);

    private final MarkerService markerService;

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final ModelMapper modelMapper;

    @Autowired
    public MarkerController(final MarkerService markerService,
                            final UserService userService,
                            final AuthenticationService authenticationService,
                            final ModelMapper modelMapper) {
        this.markerService = markerService;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<MarkerGetDto> save(@Valid @RequestBody final MarkerPostDto markerPostDto) {
        final String currentlyAuthenticatedUser = authenticationService.getAuthenticatedEmail();
        LOGGER.debug("Found currently authenticated User.");

        final User searchedUser = userService.findByEmail(currentlyAuthenticatedUser);
        LOGGER.debug("Found searched User. [id={}]", searchedUser.getId());

        final Marker marker = markerService.save(searchedUser, modelMapper.map(markerPostDto, Marker.class));
        LOGGER.info("Finish process of creating new Marker for User. [id={}]", searchedUser.getId());
        return new ResponseEntity<>(modelMapper.map(marker, MarkerGetDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkerGetDto> findById(@PathVariable final UUID id) {
        final Marker searchedMarker = markerService.findById(id);
        LOGGER.info("Found searched Marker. [id={}]", id);
        return new ResponseEntity<>(modelMapper.map(searchedMarker, MarkerGetDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MarkerGetDto>> findAll() {
        final List<Marker> searchedMarkers = markerService.findAll();
        LOGGER.info("Found all searched Markers. [size={}]", searchedMarkers.size());
        return new ResponseEntity<>(mapList(searchedMarkers), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarkerGetDto> update(@PathVariable final UUID id, @Valid @RequestBody final MarkerPutDto markerPutDto) {
        final Marker updatedMarker = markerService.update(modelMapper.map(markerPutDto, Marker.class));
        LOGGER.info("Finish process of updating existing Marker. [id={}]", id);
        return new ResponseEntity<>(modelMapper.map(updatedMarker, MarkerGetDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        final Marker searchedMarker = markerService.findById(id);
        LOGGER.debug("Found searched Marker. [id={}]", id);

        markerService.delete(searchedMarker);
        LOGGER.info("Finish process of deleting existing Marker. [id={}]", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<MarkerGetDto> mapList(final List<Marker> markers) {
        return markers
                .stream()
                .map(element -> modelMapper.map(element, MarkerGetDto.class))
                .collect(Collectors.toList());
    }
}
