package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.base.BaseEntity;

import java.util.List;

public interface AbstractEntityService<E extends BaseEntity> {

    E save(final E entity);

    E findById(final Long id);

    List<E> findAll();

    void delete(final E entity);

}
