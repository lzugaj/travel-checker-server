package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;

import java.util.List;

public interface RoleService {

    Role findById(final Long id);

    Role findByRoleType(final RoleType roleType);

    List<Role> findAll();

}
