package com.luv2code.travelchecker.repository;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface AbstractEntityRepository<E extends BaseEntity> extends JpaRepository<E, UUID> {

}
