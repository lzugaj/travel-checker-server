package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.base.BaseEntity;

import java.util.List;
import java.util.UUID;

public interface AbstractEntityService<E extends BaseEntity> {

    E save(final E entity);

    E findById(final UUID id);

    List<E> findAll();

    void delete(final E entity);

}
