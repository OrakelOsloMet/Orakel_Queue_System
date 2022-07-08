package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.DTO;
import com.orakeloslomet.persistance.models.DomainEntity;
import com.orakeloslomet.mappers.DtoMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
abstract class CrudServiceTest<D extends DTO, E extends DomainEntity> {

    private final JpaRepository repository;
    private final DtoMapper mapper;

    protected CrudServiceTest(final DtoMapper mapper, final JpaRepository repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    protected void setupSaveAndReturnDto(final D dto, final E entity) {
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);
    }

    @SuppressWarnings("unchecked")
    protected void verifySaveAndReturnDto(final E entity) {
        verify(repository).save(entity);
        verify(mapper).toDto(entity);
    }

}
