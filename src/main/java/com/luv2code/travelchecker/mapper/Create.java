package com.luv2code.travelchecker.mapper;

public interface Create<E, D> {

    E dtoToEntity(final D dto);

}
