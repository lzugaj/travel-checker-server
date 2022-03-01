package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.configuration.MapboxProperties;
import com.luv2code.travelchecker.service.MapboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapboxServiceImpl implements MapboxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapboxServiceImpl.class);

    private final MapboxProperties mapboxProperties;

    @Autowired
    public MapboxServiceImpl(final MapboxProperties mapboxProperties) {
        this.mapboxProperties = mapboxProperties;
    }

    @Override
    public String getToken() {
        final String token = mapboxProperties.getToken();
        LOGGER.debug("Retrieve Mapbox token from configuration file.");
        return token;
    }
}
