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

@Service
public class RoleServiceImpl extends AbstractEntityServiceImpl<Role, RoleRepository> implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository) {
        super(roleRepository, Role.class);
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public Role findByRoleType(final RoleType roleType) {
        LOGGER.info("Searching Role with name: ´{}´.", roleType.name());
        return roleRepository.findByName(roleType)
                .orElseThrow(() -> {
                    LOGGER.error("Role with name: ´{}´.", roleType.name() + " was not found.");
                    return new EntityNotFoundException(
                            "Role with name: " + roleType.name() + " was not found.");
                });
    }

    @Override
    public List<Role> findAll() {
        return super.findAll();
    }
}
