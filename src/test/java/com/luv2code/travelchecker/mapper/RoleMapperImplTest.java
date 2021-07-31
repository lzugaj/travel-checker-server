package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import com.luv2code.travelchecker.mapper.impl.RoleMapperImpl;
import com.luv2code.travelchecker.utils.RoleUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class RoleMapperImplTest {

    @InjectMocks
    private RoleMapperImpl roleMapper;

    @Mock
    private ModelMapper modelMapper;

    private Role adminRole;
    private List<Role> roles;

    @BeforeEach
    public void setup() {
        // Role
        adminRole = RoleUtil.createRole(1L, RoleType.ADMIN, "Admin role");
        Role userRole = RoleUtil.createRole(2L, RoleType.USER, "User role");

        // RoleGetDto
        RoleGetDto adminRoleDto = RoleUtil.createRoleGetDto(adminRole);
        RoleGetDto userRoleDto = RoleUtil.createRoleGetDto(userRole);

        roles = Arrays.asList(adminRole, userRole);

        Mockito.when(modelMapper.map(adminRole, RoleGetDto.class)).thenReturn(adminRoleDto);
        Mockito.when(modelMapper.map(userRole, RoleGetDto.class)).thenReturn(userRoleDto);
    }

    @Test
    @DisplayName("entityToDto(Role) - should return RoleGetDto")
    public void should_Return_RoleGetDto_When_Correctly_Mapped() {
        final RoleGetDto searchedDtoRole = roleMapper.entityToDto(adminRole);

        Assertions.assertNotNull(searchedDtoRole);
        Assertions.assertEquals("ADMIN", searchedDtoRole.getName());
    }

    @Test
    @DisplayName("entitiesToDto(List<Role>) - should return RoleGetDto list")
    public void should_Return_RoleGetDto_List_When_Correctly_Mapped() {
        final List<RoleGetDto> searchedDtoRoles = roleMapper.entitiesToDto(roles);

        Assertions.assertNotNull(searchedDtoRoles);
        Assertions.assertEquals(2, searchedDtoRoles.size());
    }
}
