package com.luv2code.travelchecker.repository;

import com.luv2code.travelchecker.domain.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {

    Optional<Coordinate> findCoordinateByLongitudeAndLatitude(final Double longitude, final Double latitude);

}
