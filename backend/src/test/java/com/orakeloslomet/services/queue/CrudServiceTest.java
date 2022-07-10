package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.DTO;
import com.orakeloslomet.mappers.DtoMapper;
import com.orakeloslomet.persistance.models.DomainEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
abstract class CrudServiceTest<D extends DTO, E extends DomainEntity> {

    protected final JpaRepository<E, Long> repository;
    protected final DtoMapper<D, E> mapper;

    protected CrudServiceTest(final DtoMapper<D, E> mapper, final JpaRepository<E, Long> repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected void setupSaveAndReturnDto(final D dto, final E entity) {
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);
    }

    protected void verifySaveAndReturnDto(final E entity) {
        verify(repository).save(entity);
        verify(mapper).toDto(entity);
    }

}
