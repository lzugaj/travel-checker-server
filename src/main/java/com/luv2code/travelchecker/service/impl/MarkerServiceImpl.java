package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.MarkerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarkerServiceImpl implements MarkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerServiceImpl.class);

    private final MarkerRepository markerRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MarkerServiceImpl(final MarkerRepository markerRepository,
                             final ModelMapper modelMapper) {
        this.markerRepository = markerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MarkerGetDto save(final UserGetDto userGetDto, final MarkerPostDto markerPostDto) {
        final Marker marker = markerRepository.save(modelMapper.map(markerPostDto, Marker.class));
        LOGGER.info("Creating new Marker with id: ´{}´.", marker.getId());
        return modelMapper.map(marker, MarkerGetDto.class);
    }

    @Override
    public MarkerGetDto findById(final Long id) {
        final Optional<Marker> marker = markerRepository.findById(id);
        if (marker.isPresent()) {
            LOGGER.info("Searching Marker with id: ´{}´.", id);
            return modelMapper.map(marker.get(), MarkerGetDto.class);
        } else {
            LOGGER.error("Cannot find Marker with id: ´{}´.", id);
            throw new EntityNotFoundException("Marker", "id", String.valueOf(id));
        }
    }

    @Override
    public List<MarkerGetDto> findAll() {
        final List<Marker> markers = markerRepository.findAll();
        final TypeToken<List<MarkerGetDto>> typeToken = new TypeToken<>() {};
        LOGGER.info("Searching all Markers.");
        return modelMapper.map(markers, typeToken.getType());
    }

//    @Override
//    public Marker update(final Marker oldMarker, final MarkerPutDto markerPutDto) {
//        final Marker updatedMarker = markerRepository.save(markerMapper.entityDtoToEntity(oldMarker, markerPutDto));
//        LOGGER.info("Updating Marker with id: ´{}´.", oldMarker.getId());
//        return updatedMarker;
//    }

    @Override
    public void delete(final Marker marker) {
        LOGGER.info("Deleting Marker with id: ´{}´.", marker.getId());
        markerRepository.delete(marker);
    }
}
