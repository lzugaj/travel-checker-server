package com.luv2code.travelchecker.repository;

import com.luv2code.travelchecker.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AbstractEntityRepository<User> {

    Optional<User> findByUsername(final String username);

}
