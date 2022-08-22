package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.DTO;
import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.mappers.DtoMapper;
import com.orakeloslomet.persistance.models.DomainEntity;
import com.orakeloslomet.persistance.models.PersistableEntity;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.services.CrudService;
import com.orakeloslomet.services.CrudServiceImpl;
import com.orakeloslomet.utilities.exceptions.NoSuchPersistedEntityException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
abstract class CrudServiceTest<D extends DTO, E extends PersistableEntity, S extends CrudServiceImpl<D, E>> {

    protected final JpaRepository<E, Long> repository;
    protected final DtoMapper<D, E> mapper;

    protected CrudServiceTest(final DtoMapper<D, E> mapper, final JpaRepository<E, Long> repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected void findByIdReturnsFoundEntity(final E expectedPersistedEntity, final D mappedDto, final S classUnderTest) {
        final Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(expectedPersistedEntity));
        when(mapper.toDto(expectedPersistedEntity)).thenReturn(mappedDto);

        //when
        final D actualResult = classUnderTest.findById(id);

        //then
        assertEquals(mappedDto, actualResult);
        verify(repository).findById(id);
        verify(mapper).toDto(expectedPersistedEntity);
    }

    protected void findByIdEntityNotFoundThrowsException(final S classUnderTest) {
        //given
        final Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        //when/then
        assertThrows(NoSuchPersistedEntityException.class, () -> classUnderTest.findById(id));
        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }

    protected void saveEntityIsSaved(final E expectedPersistedEntity, final D toBeSavedDTO, final S classUnderTest) {
        //given
        when(mapper.toEntity(toBeSavedDTO)).thenReturn(expectedPersistedEntity);
        setupSaveAndReturnDto(toBeSavedDTO, expectedPersistedEntity);

        //when
        final D actualResult = classUnderTest.save(toBeSavedDTO);

        //then
        assertEquals(toBeSavedDTO, actualResult);
        verify(mapper).toEntity(toBeSavedDTO);
        verifySaveAndReturnDto(expectedPersistedEntity);
    }

    protected void deleteByIdEntityNotFound(final S classUnderTest) {
        //given
        final Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        //when/then
        assertThrows(NoSuchPersistedEntityException.class, () -> classUnderTest.deleteById(id));
        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    protected void deleteByIdEntityFound(final S crudService, final E persistableEntity) {
        //given
        final Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(persistableEntity));

        //when
        crudService.deleteById(id);

        //then
        verify(repository).findById(id);
        verify(repository).deleteById(id);
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
