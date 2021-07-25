package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.base.BaseEntity;

import java.util.List;

public interface ViewService<T extends BaseEntity> {

    T findById(final Long id);

    List<T> findAll();

}
