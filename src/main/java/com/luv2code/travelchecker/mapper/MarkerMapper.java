package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;

public interface MarkerMapper extends Create<Marker, MarkerPostDto>, View<Marker, MarkerGetDto>, Update<Marker, MarkerPutDto> {

}
