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

    @Autowired
    public UserMapperImpl(final RoleService roleService,
                          final RoleMapper roleMapper,
                          final MarkerMapper markerMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
        this.markerMapper = markerMapper;
    }

    @Override
    public User dtoToEntity(final UserPostDto dto) {
        LOGGER.info("Start mapping UserPostDto to User.");
        final User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setCreatedAt(dto.getCreatedAt());

        final Role userRole = roleService.findByRoleType(RoleType.USER);
        LOGGER.info("Successfully founded Role with name: ´{}´.", userRole.getName().name());

        user.addRole(userRole);
        user.setMarkers(new ArrayList<>());
        return user;
    }

    @Override
    public User dtoToEntity(final User entity, final UserPutDto dto) {
        LOGGER.info("Start mapping UserPutDto to User.");
        entity.setId(entity.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        return entity;
    }

    @Override
    public UserGetDto entityToDto(final User entity) {
        LOGGER.info("Start mapping User to UserGetDto.");
        final UserGetDto searchedDtoUser = new UserGetDto();
        searchedDtoUser.setId(entity.getId());
        searchedDtoUser.setFirstName(entity.getFirstName());
        searchedDtoUser.setLastName(entity.getLastName());
        searchedDtoUser.setEmail(entity.getEmail());
        searchedDtoUser.setUsername(entity.getUsername());

        final List<RoleGetDto> searchedDtoRoles = roleMapper.entitiesToDto(entity.getRoles());
        searchedDtoUser.setRoles(searchedDtoRoles);

        final List<MarkerGetDto> searchedDtoMarkers = markerMapper.entitiesToDto(entity.getMarkers());
        searchedDtoUser.setMarkers(searchedDtoMarkers);
        return searchedDtoUser;
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
