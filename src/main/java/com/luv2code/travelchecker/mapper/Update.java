package com.luv2code.travelchecker.mapper;

public interface Update<E, P> {

    E dtoToEntity(final E entity, final P dto);

}
