package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Marker;

import java.util.List;

public interface MarkerService {

    Marker save(final Marker marker);

    Marker findById(final Long id);

    Marker findByCoordinate(final Double longitude, final Double latitude);

    List<Marker> findAll();

    Marker update(final Marker oldMarker, final Marker newMarker);

    void delete(final Marker marker);

}
