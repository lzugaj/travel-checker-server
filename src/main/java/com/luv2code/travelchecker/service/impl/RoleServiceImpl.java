package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.RoleRepository;
import com.luv2code.travelchecker.service.RoleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository,
                           final ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleGetDto findById(final Long id) {
        final Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            LOGGER.info("Searching Role with id: ´{}´.", id);
            return modelMapper.map(role.get(), RoleGetDto.class);
        } else {
            LOGGER.error("Cannot find Role with id: ´{}´.", id);
            throw new EntityNotFoundException(
                    "Role", "id", String.valueOf(id)
            );
        }
    }

    @Override
    public RoleGetDto findByRoleType(final RoleType roleType) {
        final Optional<Role> role = roleRepository.findByName(roleType);
        if (role.isPresent()) {
            LOGGER.info("Searching Role with name: ´{}´.", roleType.name());
            return modelMapper.map(role.get(), RoleGetDto.class);
        } else {
            LOGGER.error("Cannot find Role with name: ´{}´.", roleType.name());
            throw new EntityNotFoundException(
                    "Role", "name", roleType.name()
            );
        }
    }

    @Override
    public List<RoleGetDto> findAll() {
        final List<Role> roles = roleRepository.findAll();
        final TypeToken<List<RoleGetDto>> typeToken = new TypeToken<>() {};
        LOGGER.info("Searching all Roles.");
        return modelMapper.map(roles, typeToken.getType());
    }
}
