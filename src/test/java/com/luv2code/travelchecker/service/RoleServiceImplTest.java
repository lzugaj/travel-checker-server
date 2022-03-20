package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.RoleRepository;
import com.luv2code.travelchecker.service.impl.RoleServiceImpl;
import com.luv2code.travelchecker.mock.RoleMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    private Role adminRole;
    private Role userRole;

    @BeforeEach
    public void setup() {
        adminRole = RoleMock.createRole(UUID.randomUUID(), RoleType.ADMIN, "ADMIN role have the highest permission in application hierarchy");
        userRole = RoleMock.createRole(UUID.fromString("1aa1b7ae-ec30-431b-9473-07e9a5bb0651"), RoleType.USER, "USER role could READ and WRITE data which is assigned only to them");

        final List<Role> roles = Collections.singletonList(userRole);

        Mockito.when(roleRepository.findById(adminRole.getId())).thenReturn(java.util.Optional.ofNullable(adminRole));
        Mockito.when(roleRepository.findByName(userRole.getName())).thenReturn(java.util.Optional.ofNullable(userRole));
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
    }

    @Test
    public void should_Return_Role_When_Id_Is_Present() {
        final Role searchedRole = roleService.findById(adminRole.getId());

        Assertions.assertEquals("ADMIN", searchedRole.getName().name());
        Assertions.assertNotNull(searchedRole);
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Present() {
        Mockito.when(roleRepository.findById(userRole.getId()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> roleService.findById(userRole.getId())
        );

        final String expectedMessage = String.format("Cannot find searched Role by given id. [id=%s]", "1aa1b7ae-ec30-431b-9473-07e9a5bb0651");
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_Role_When_Name_Is_Present() {
        final Role searchedRole = roleService.findByRoleType(RoleType.USER);

        Assertions.assertEquals("USER", searchedRole.getName().name());
        Assertions.assertNotNull(searchedRole);
    }

    @Test
    public void should_Throw_Exception_When_Name_Is_Not_Present() {
        Mockito.when(roleRepository.findByName(RoleType.ADMIN))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> roleService.findByRoleType(RoleType.ADMIN)
        );

        final String expectedMessage = String.format("Cannot find Role by given name. [name=%s]", SecurityConstants.ADMIN_ROLE);
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Find_All_Roles() {
        final List<Role> searchedRoles = roleService.findAll();

        Assertions.assertEquals(1, searchedRoles.size());
        Assertions.assertNotNull(searchedRoles);
    }
}
