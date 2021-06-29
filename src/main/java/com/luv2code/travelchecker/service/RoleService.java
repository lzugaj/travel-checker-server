package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;

import java.util.List;

public interface RoleService {

    RoleGetDto findById(final Long id);

    RoleGetDto findByRoleType(final RoleType roleType);

    List<RoleGetDto> findAll();

}
