package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.AbstractEntityRepository;
import com.luv2code.travelchecker.service.AbstractEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
public abstract class AbstractEntityServiceImpl<E extends BaseEntity, R extends AbstractEntityRepository<E>> implements AbstractEntityService<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityServiceImpl.class);

    private final R crudRepository;

    private final Class<E> entityClass;

    protected AbstractEntityServiceImpl(final R crudRepository,
                                     final Class<E> entityClass) {
        this.crudRepository = crudRepository;
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public E save(final E entity) {
        return crudRepository.save(entity);
    }

    @Override
    public E findById(final UUID id) {
        return crudRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find searched {} by given id. [id={}]", entityClass.getSimpleName(), id);
                    return new EntityNotFoundException(
                            String.format("Cannot find searched %s by given id. [id=%s]", entityClass.getSimpleName(), id)
                    );
                });
    }

    @Override
    public List<E> findAll() {
        return crudRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(final E entity) {
        crudRepository.delete(entity);
    }
}
