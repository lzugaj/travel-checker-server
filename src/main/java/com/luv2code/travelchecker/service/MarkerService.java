package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.User;

import java.util.List;

public interface MarkerService {

    Marker save(final User user, final Marker marker);

    Marker findById(final Long id);

    List<Marker> findAll();

    Marker update(final Marker marker);

    void delete(final Marker marker);

}
