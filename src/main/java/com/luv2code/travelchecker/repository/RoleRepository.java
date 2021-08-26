package com.luv2code.travelchecker.repository;

import com.luv2code.travelchecker.domain.Role;
import com.luv2code.travelchecker.domain.enums.RoleType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends AbstractEntityRepository<Role> {

    Optional<Role> findByName(final RoleType roleType);

}
