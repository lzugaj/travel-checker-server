package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.configuration.MapboxConfiguration;
import com.luv2code.travelchecker.service.MapboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapboxServiceImpl implements MapboxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapboxServiceImpl.class);

    private final MapboxConfiguration mapboxConfiguration;

    @Autowired
    public MapboxServiceImpl(final MapboxConfiguration mapboxConfiguration) {
        this.mapboxConfiguration = mapboxConfiguration;
    }

    @Override
    public String fetchToken() {
        LOGGER.info("Fetching Mapbox token from configuration file.");
        return mapboxConfiguration.getToken();
    }
}
