package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.RoleRepository;
import com.luv2code.travelchecker.service.RoleService;
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

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(final Long id) {
        final Optional<Role> searchedRole = roleRepository.findById(id);
        if (searchedRole.isPresent()) {
            LOGGER.info("Searching Role with id: ´{}´.", id);
            return searchedRole.get();
        } else {
            LOGGER.error("Cannot find Role with id: ´{}´.", id);
            throw new EntityNotFoundException(
                    "Role", "id", String.valueOf(id));
        }
    }

    @Override
    public Role findByRoleType(final RoleType roleType) {
        final Optional<Role> searchedRole = roleRepository.findByName(roleType);
        if (searchedRole.isPresent()) {
            LOGGER.info("Searching Role with name: ´{}´.", roleType.name());
            return searchedRole.get();
        } else {
            LOGGER.error("Cannot find Role with name: ´{}´.", roleType.name());
            throw new EntityNotFoundException(
                    "Role", "name", roleType.name());
        }
    }

    @Override
    public List<Role> findAll() {
        final List<Role> searchedRoles = roleRepository.findAll();
        LOGGER.info("Searching all Roles.");
        return searchedRoles;
    }
}
