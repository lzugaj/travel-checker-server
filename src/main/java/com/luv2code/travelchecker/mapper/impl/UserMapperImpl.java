package com.luv2code.travelchecker.mapper.impl;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;
import com.luv2code.travelchecker.mapper.MarkerMapper;
import com.luv2code.travelchecker.mapper.RoleMapper;
import com.luv2code.travelchecker.mapper.UserMapper;
import com.luv2code.travelchecker.service.RoleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMapperImpl.class);

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    private final MarkerMapper markerMapper;

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapperImpl(final RoleService roleService,
                          final RoleMapper roleMapper,
                          final MarkerMapper markerMapper,
                          final ModelMapper modelMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
        this.markerMapper = markerMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public User dtoToEntity(final UserPostDto dto) {
        LOGGER.info("Start mapping UserPostDto to User.");
        final User user = modelMapper.map(dto, User.class);
        user.addRole(findUserRole());
        user.setMarkers(new ArrayList<>());
        return user;
    }

    private Role findUserRole() {
        final Role userRole = roleService.findByRoleType(RoleType.USER);
        LOGGER.info("Successfully founded Role with name: ´{}´.", userRole.getName().name());
        return userRole;
    }

    @Override
    public User dtoToEntity(final User entity, final UserPutDto dto) {
        LOGGER.info("Start mapping UserPutDto to User.");
        entity.setId(entity.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setModifiedAt(dto.getModifiedAt());
        return entity;
    }

    @Override
    public UserGetDto entityToDto(final User entity) {
        LOGGER.info("Start mapping User to UserGetDto.");
        final UserGetDto searchedDtoUser = modelMapper.map(entity, UserGetDto.class);
        searchedDtoUser.setId(entity.getId());
        searchedDtoUser.setRoles(getUserRoles(entity));
        searchedDtoUser.setMarkers(getUserMarkers(entity));
        return searchedDtoUser;
    }

    private List<RoleGetDto> getUserRoles(final User entity) {
        final List<RoleGetDto> searchedRoles = roleMapper.entitiesToDto(entity.getRoles());
        LOGGER.info("Successfully founded ´{}´ roles for User with id: ´{}´.", searchedRoles.size(), entity.getId());
        return searchedRoles;
    }

    private List<MarkerGetDto> getUserMarkers(final User entity) {
        final List<MarkerGetDto> searchedMarkers = markerMapper.entitiesToDto(entity.getMarkers());
        LOGGER.info("Successfully founded ´{}´ markers for User with id: ´{}´.", searchedMarkers.size(), entity.getId());
        return searchedMarkers;
    }

    @Override
    public List<UserGetDto> entitiesToDto(final List<User> entities) {
        LOGGER.info("Start mapping User to UserGetDto list.");
        final List<UserGetDto> searchedDtoUsers = new ArrayList<>();
        for (User user : entities) {
            searchedDtoUsers.add(entityToDto(user));
        }

        return searchedDtoUsers;
    }
}
