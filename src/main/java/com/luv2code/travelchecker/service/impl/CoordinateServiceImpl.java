package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.CoordinateService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordinateServiceImpl implements CoordinateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinateServiceImpl.class);

    private final CoordinateRepository coordinateRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CoordinateServiceImpl(final CoordinateRepository coordinateRepository,
                                 final ModelMapper modelMapper) {
        this.coordinateRepository = coordinateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CoordinateGetDto findById(final Long id) {
        final Optional<Coordinate> coordinate = coordinateRepository.findById(id);
        if (coordinate.isPresent()) {
            LOGGER.info("Searching Coordinate with id: ´{}´.", id);
            return modelMapper.map(coordinate.get(), CoordinateGetDto.class);
        } else {
            LOGGER.error("Cannot find Coordinate with id: ´{}´.", id);
            throw new EntityNotFoundException("Coordinate", "id", String.valueOf(id));
        }
    }

    @Override
    public List<CoordinateGetDto> findAll() {
        final List<Coordinate> coordinates = coordinateRepository.findAll();
        final TypeToken<List<CoordinateGetDto>> typeToken = new TypeToken<>() {};
        LOGGER.info("Searching all Coordinates.");
        return modelMapper.map(coordinates, typeToken.getType());
    }
}
