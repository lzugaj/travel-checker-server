package com.luv2code.travelchecker.configuration;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        // Role Mapper
        roleToRoleGetDto(modelMapper);

        // User Mapper
        userToUserGetDto(modelMapper);
        userPostDtoToUser(modelMapper);
        userPutDtoToUser(modelMapper);

        // Coordinate Mapper
        coordinateToCoordinateGetDto(modelMapper);

        // Marker Mapper
        markerToMarkerMarkerGetDto(modelMapper);
        markerPostDtoToMarker(modelMapper);
        markerPutDtoToMarker(modelMapper);

        return new ModelMapper();
    }

    private void roleToRoleGetDto(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(Role.class, RoleGetDto.class)
                .addMapping(Role::getName, RoleGetDto::setName);
    }

    private void userToUserGetDto(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(User.class, UserGetDto.class)
                .addMapping(User::getId, UserGetDto::setId)
                .addMapping(User::getFirstName, UserGetDto::setFirstName)
                .addMapping(User::getLastName, UserGetDto::setLastName)
                .addMapping(User::getEmail, UserGetDto::setEmail)
                .addMapping(User::getRoles, UserGetDto::setRoles)
                .addMapping(User::getMarkers, UserGetDto::setMarkers);
    }

    private void userPostDtoToUser(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(UserPostDto.class, User.class)
                .addMapping(UserPostDto::getFirstName, User::setFirstName)
                .addMapping(UserPostDto::getLastName, User::setLastName)
                .addMapping(UserPostDto::getEmail, User::setEmail)
                .addMapping(UserPostDto::getPassword, User::setPassword)
                .addMapping(UserPostDto::getConfirmationPassword, User::setConfirmationPassword)
                .addMapping(UserPostDto::getCreatedAt, User::setCreatedAt)
                .addMapping(UserPostDto::getMarkers, User::setMarkers);
    }

    private void userPutDtoToUser(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(UserPutDto.class, User.class)
                .addMapping(UserPutDto::getId, User::setId)
                .addMapping(UserPutDto::getFirstName, User::setFirstName)
                .addMapping(UserPutDto::getLastName, User::setLastName)
                .addMapping(UserPutDto::getEmail, User::setEmail)
                .addMapping(UserPutDto::getModifiedAt, User::setModifiedAt);
    }

    private void coordinateToCoordinateGetDto(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(Coordinate.class, CoordinateGetDto.class)
                .addMapping(Coordinate::getId, CoordinateGetDto::setId)
                .addMapping(Coordinate::getLongitude, CoordinateGetDto::setLongitude)
                .addMapping(Coordinate::getLatitude, CoordinateGetDto::setLatitude);
    }

    private void markerToMarkerMarkerGetDto(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(Marker.class, MarkerGetDto.class)
                .addMapping(Marker::getId, MarkerGetDto::setId)
                .addMapping(Marker::getName, MarkerGetDto::setName)
                .addMapping(Marker::getDescription, MarkerGetDto::setDescription)
                .addMapping(Marker::getEventDate, MarkerGetDto::setEventDate)
                .addMapping(Marker::getGrade, MarkerGetDto::setGrade)
                .addMapping(Marker::getShouldVisitAgain, MarkerGetDto::setShouldVisitAgain)
                .addMapping(Marker::getCoordinate, MarkerGetDto::setCoordinate);
    }

    private void markerPostDtoToMarker(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(MarkerPostDto.class, Marker.class)
                .addMapping(MarkerPostDto::getName, Marker::setName)
                .addMapping(MarkerPostDto::getDescription, Marker::setDescription)
                .addMapping(MarkerPostDto::getEventDate, Marker::setEventDate)
                .addMapping(MarkerPostDto::getGrade, Marker::setGrade)
                .addMapping(MarkerPostDto::getShouldVisitAgain, Marker::setShouldVisitAgain)
                .addMapping(MarkerPostDto::getCoordinatePostDto, Marker::setCoordinate)
                .addMapping(MarkerPostDto::getCreatedAt, Marker::setCreatedAt);
    }

    private void markerPutDtoToMarker(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(MarkerPutDto.class, Marker.class)
                .addMapping(MarkerPutDto::getId, Marker::setId)
                .addMapping(MarkerPutDto::getName, Marker::setName)
                .addMapping(MarkerPutDto::getDescription, Marker::setDescription)
                .addMapping(MarkerPutDto::getEventDate, Marker::setEventDate)
                .addMapping(MarkerPutDto::getGrade, Marker::setGrade)
                .addMapping(MarkerPutDto::getShouldVisitAgain, Marker::setShouldVisitAgain)
                .addMapping(MarkerPutDto::getCoordinate, Marker::setCoordinate)
                .addMapping(MarkerPutDto::getModifiedAt, Marker::setModifiedAt);
    }
}
