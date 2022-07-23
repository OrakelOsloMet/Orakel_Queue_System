package com.orakeloslomet.services;

import com.orakeloslomet.dtos.DTO;
import com.orakeloslomet.mappers.DtoMapper;
import com.orakeloslomet.persistance.models.PersistableEntity;
import com.orakeloslomet.persistance.repositories.PersistableEntityRepository;
import com.orakeloslomet.utilities.exceptions.NoSuchPersistedEntityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 08/07/2022 at 19:38
 */

@Slf4j
@RequiredArgsConstructor
public abstract class CrudServiceImpl<T extends DTO, R extends PersistableEntity> implements CrudService<T> {

    protected final DtoMapper<T, R> dtoMapper;
    protected final PersistableEntityRepository<R> repository;

    @Override
    public List<T> findAll() {
        return repository.findAll().stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public T findById(final Long id) {
        return repository.findById(id)
                .map(dtoMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchPersistedEntityException(String.format("Could not find persisted entity with ID %s", id))
                );
    }

    @Override
    public T save(final T dtoObject) {
        return saveAndReturnDto(dtoMapper.toEntity(dtoObject));
    }

    @Override
    public T update(final T dtoObject, final Long id) {
        return repository.findById(id)
                .map(persistableEntity -> {
                    final R mappedEntity = dtoMapper.toEntity(dtoObject);
                    mappedEntity.setId(persistableEntity.getId());
                    mappedEntity.setCreatedDate(persistableEntity.getCreatedDate());
                    return saveAndReturnDto(mappedEntity);
                }).orElseThrow(() ->
                        new NoSuchPersistedEntityException(String.format("Could not find persisted entity for %s with ID %s",
                                dtoObject.getClass().getSimpleName(), id))
                );
    }

    @Override
    public void deleteById(final Long id) {
        repository.findById(id)
                .orElseThrow(() ->
                        new NoSuchPersistedEntityException(String.format("Could not find persisted entity with ID %s", id)));

        repository.deleteById(id);
    }

    private T saveAndReturnDto(final R persistableEntity) {
        final R savedEntity = repository.save(persistableEntity);
        return dtoMapper.toDto(savedEntity);
    }
}
