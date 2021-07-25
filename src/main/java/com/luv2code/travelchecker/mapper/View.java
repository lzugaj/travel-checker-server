package com.luv2code.travelchecker.mapper;

import java.util.List;

public interface View<E, D> {

    D entityToDto(final E entity);

    List<D> entitiesToDto(final List<E> entities);

}
