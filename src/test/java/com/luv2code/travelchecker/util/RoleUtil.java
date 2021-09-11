package com.luv2code.travelchecker.util;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;

public class RoleUtil {

    public static Role createRole(final Long id, final RoleType roleType, final String description) {
        final Role role = new Role();
        role.setId(id);
        role.setName(roleType);
        role.setDescription(description);
        return role;
    }

    public static RoleGetDto createRoleGetDto(final Role role) {
        RoleGetDto roleGetDto = new RoleGetDto();
        roleGetDto.setName(role.getName().name());
        return roleGetDto;
    }
}
