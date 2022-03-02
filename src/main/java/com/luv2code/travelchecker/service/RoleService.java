package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    Role findById(final UUID id);

    Role findByRoleType(final RoleType roleType);

    List<Role> findAll();

}
