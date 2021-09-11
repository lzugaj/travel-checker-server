package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.AbstractEntityRepository;
import com.luv2code.travelchecker.service.AbstractEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractEntityServiceImpl<E extends BaseEntity, R extends AbstractEntityRepository<E>> implements AbstractEntityService<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityServiceImpl.class);

    private final R crudRepository;

    private final Class<E> entityClass;

    public AbstractEntityServiceImpl(final R crudRepository,
                                     final Class<E> entityClass) {
        this.crudRepository = crudRepository;
        this.entityClass = entityClass;
    }

    @Override
    public E save(final E entity) {
        final E newEntity = crudRepository.save(entity);
        LOGGER.info("Successfully created {} with id: ´{}´.", entityClass.getSimpleName(), entity.getId());
        return newEntity;
    }

    @Override
    public E findById(final Long id) {
        LOGGER.info("Searching {} with id: ´{}´.", entityClass.getSimpleName(), id);
        return crudRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find {} with id: ´{}´.", entityClass.getSimpleName(), id);
                    return new EntityNotFoundException(entityClass.getSimpleName(), "id", String.valueOf(id));
                });
    }

    @Override
    public List<E> findAll() {
        LOGGER.info("Searching all {}.", entityClass.getSimpleName());
        return crudRepository.findAll();
    }

    @Override
    public void delete(final E entity) {
        LOGGER.info("Deleting {} with id: ´{}´.", entityClass.getSimpleName(), entity.getId());
        crudRepository.delete(entity);
    }
}
