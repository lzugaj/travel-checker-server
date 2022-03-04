package com.luv2code.travelchecker.repository;

import com.luv2code.travelchecker.domain.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<ResetPasswordToken, UUID> {

    Optional<ResetPasswordToken> findByToken(final String token);

}
