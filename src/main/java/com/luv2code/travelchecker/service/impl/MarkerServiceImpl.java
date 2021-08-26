package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerServiceImpl extends AbstractEntityServiceImpl<Marker, MarkerRepository> implements MarkerService {

    @Autowired
    public MarkerServiceImpl(final MarkerRepository markerRepository) {
        super(markerRepository, Marker.class);
    }

    @Override
    public Marker save(final User user, final Marker marker) {
        marker.getCoordinate().addMarker(marker);
        marker.addUser(user);
        return super.save(marker);
    }

    @Override
    public Marker findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<Marker> findAll() {
        return super.findAll();
    }

    @Override
    public Marker update(final Marker marker) {
        return super.save(marker);
    }

    @Override
    public void delete(final Marker marker) {
        super.delete(marker);
    }
}
