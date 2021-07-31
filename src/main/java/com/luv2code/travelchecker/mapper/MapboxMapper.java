package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.dto.mapbox.MapboxGetDto;

public interface MapboxMapper<D extends MapboxGetDto> {

    D entityToDto(final String token);

}
