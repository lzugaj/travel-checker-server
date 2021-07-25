package com.luv2code.travelchecker.mapper.impl;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleMapperImpl implements RoleMapper {

    public static final Logger LOGGER = LoggerFactory.getLogger(RoleMapperImpl.class);

    @Override
    public RoleGetDto entityToDto(final Role entity) {
        LOGGER.info("Start mapping Role to RoleGetDto.");
        final RoleGetDto roleGetDto = new RoleGetDto();
        roleGetDto.setName(entity.getName().name());
        return roleGetDto;
    }

    @Override
    public List<RoleGetDto> entitiesToDto(final List<Role> entities) {
        LOGGER.info("Start mapping Role to RoleGetDto list.");
        final List<RoleGetDto> searchedDtoRoles = new ArrayList<>();
        for (Role role : entities) {
            searchedDtoRoles.add(entityToDto(role));
        }

        return searchedDtoRoles;
    }
}
