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
import java.util.UUID;

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
    public Role findById(final UUID id) {
        return super.findById(id);
    }

    @Override
    public Role findByRoleType(final RoleType roleType) {
        return roleRepository.findByName(roleType)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find Role by given name. [name={}]", roleType.name());
                    return new EntityNotFoundException(
                            String.format("Cannot find Role by given name. [name=%s]", roleType.name())
                    );
                });
    }

    @Override
    public List<Role> findAll() {
        LOGGER.debug("Searching all Roles.");
        return super.findAll();
    }
}
