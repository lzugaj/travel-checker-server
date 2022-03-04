package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.MarkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MarkerServiceImpl extends AbstractEntityServiceImpl<Marker, MarkerRepository> implements MarkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerServiceImpl.class);

    @Autowired
    public MarkerServiceImpl(final MarkerRepository markerRepository) {
        super(markerRepository, Marker.class);
    }

    @Override
    public Marker save(final User user, final Marker marker) {
        LOGGER.info("Begin process of saving new Marker for User. [id={}]", user.getId());
        marker.getCoordinate().addMarker(marker);
        marker.addUser(user);
        return super.save(marker);
    }

    @Override
    public Marker findById(final UUID id) {
        LOGGER.debug("Searching Marker by given id. [id={}]", id);
        return super.findById(id);
    }

    @Override
    public List<Marker> findAll() {
        LOGGER.debug("Searching all Markers.");
        return super.findAll();
    }

    @Override
    public Marker update(final Marker marker) {
        LOGGER.info("Begin process of updating existing Marker. [id={}]", marker.getId());
        return super.save(marker);
    }

    @Override
    public void delete(final Marker marker) {
        LOGGER.info("Begin process of deleting existing Marker. [id={}]", marker.getId());
        super.delete(marker);
    }
}
