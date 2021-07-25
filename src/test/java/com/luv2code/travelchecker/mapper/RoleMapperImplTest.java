package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.mapper.impl.RoleMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class RoleMapperImplTest {

    @InjectMocks
    private RoleMapperImpl roleMapper;

    private Role userRole;
    private Role adminRole;

    private RoleGetDto userRoleDto;
    private RoleGetDto adminRoleDto;

    private List<Role> roles;
    private List<RoleGetDto> dtoRoles;

    @BeforeEach
    public void setup() {
        // Role
        adminRole = createRole(1L, RoleType.ADMIN, "Admin role");
        userRole = createRole(2L, RoleType.USER, "User role");

        // RoleGetDto
        adminRoleDto = createRoleGetDto(adminRole);
        userRoleDto = createRoleGetDto(userRole);

        roles = Arrays.asList(adminRole, userRole);
        dtoRoles = Arrays.asList(adminRoleDto, userRoleDto);
    }

    @Test
    public void should_Return_RoleGetDto_When_Correctly_Mapped() {
        final RoleGetDto searchedDtoRole = roleMapper.entityToDto(adminRole);

        Assertions.assertNotNull(searchedDtoRole);
        Assertions.assertEquals("ADMIN", searchedDtoRole.getName());
    }

    @Test
    public void should_Return_RoleGetDto_List_When_Correctly_Mapped() {
        final List<RoleGetDto> searchedDtoRoles = roleMapper.entitiesToDto(roles);

        Assertions.assertNotNull(searchedDtoRoles);
        Assertions.assertEquals(2, searchedDtoRoles.size());
    }

    private Role createRole(final Long id, final RoleType roleType, final String description) {
        final Role role = new Role();
        role.setId(id);
        role.setName(roleType);
        role.setDescription(description);
        return role;
    }

    private RoleGetDto createRoleGetDto(final Role role) {
        RoleGetDto roleGetDto = new RoleGetDto();
        roleGetDto.setName(role.getName().name());
        return roleGetDto;
    }
}
