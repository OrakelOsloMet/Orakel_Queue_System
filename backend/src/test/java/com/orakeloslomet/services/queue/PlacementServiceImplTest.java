package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.mappers.PlacementMapper;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.repositories.queue.PlacementRepository;
import com.orakeloslomet.utilities.exceptions.NoSuchPersistedEntityException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class PlacementServiceImplTest extends CrudServiceTest<PlacementDTO, Placement, PlacementServiceImpl> {

    @InjectMocks
    private PlacementServiceImpl classUnderTest;

    protected PlacementServiceImplTest(@Mock final PlacementMapper mapper, @Mock final PlacementRepository repository) {
        super(mapper, repository);
    }

    @Nested
    class findAll {

        @Test
        void returnsAllFoundEntities() {
            //given
            final List<Placement> domainPlacements = createPlacements();
            given(repository.findAll()).willReturn(domainPlacements);
            domainPlacements.forEach(placement -> given(mapper.toDto(placement)).willReturn(toDTO(placement)));

            //when
            final List<PlacementDTO> actualResults = classUnderTest.findAll();

            //then
            assertEquals(domainPlacements.size(), actualResults.size());
            for (int i = 0; i < domainPlacements.size(); i++) {
                assertEquals(toDTO(domainPlacements.get(0)), actualResults.get(0));
            }
            verify(repository).findAll();
            verify(mapper, times(domainPlacements.size())).toDto(any(Placement.class));
        }
    }

    @Nested
    class findById {

        @Test
        void returnsFoundEntity() {
            //given
            final Placement foundById = createPlacement(1);
            final PlacementDTO mappedDto = toDTO(foundById);
            findByIdReturnsFoundEntity(foundById, mappedDto, classUnderTest);
        }

        @Test
        void throwsNoSuchElementExceptionIfNotFound() {
            findByIdEntityNotFoundThrowsException(classUnderTest);
        }
    }

    @Nested
    class save {

        @Test
        void entityIsMappedAndPassedToRepository() {
            //given
            final Placement domainPlacement = createPlacement(1);
            final PlacementDTO toBeSaved = toDTO(domainPlacement);
            saveEntityIsSaved(domainPlacement, toBeSaved, classUnderTest);
        }
    }

    @Nested
    class update {

        private final Long ID = 1L;
        private final String UPDATED = "updated";
        private final Integer NUMBER = 1;

        @Test
        void updatedEntityIsPassedToRepository() {
            //given
            final Placement domainPlacement = createPlacement(ID.intValue());
            final PlacementDTO updatedDTO = PlacementDTO.builder()
                    .id(domainPlacement.getId())
                    .createdDate(domainPlacement.getCreatedDate())
                    .name(UPDATED)
                    .number(NUMBER)
                    .build();
            when(repository.findById(ID)).thenReturn(Optional.of(domainPlacement));
            when(mapper.toEntity(updatedDTO)).thenReturn(domainPlacement);
            domainPlacement.setName(UPDATED);
            domainPlacement.setNumber(NUMBER);
            setupSaveAndReturnDto(updatedDTO, domainPlacement);

            //when
            final PlacementDTO actualResult = classUnderTest.update(updatedDTO, ID);

            //then
            assertEquals(updatedDTO, actualResult);
            verify(repository).findById(updatedDTO.getId());
            verifySaveAndReturnDto(domainPlacement);
        }

        @Test
        void throwsNoSuchPersistedEntityExceptionIfNotFound() {
            //given
            final PlacementDTO updatedDTO = toDTO(createPlacement(ID.intValue()));
            when(repository.findById(ID)).thenReturn(Optional.empty());

            //when/then
            assertThrows(NoSuchPersistedEntityException.class, () -> classUnderTest.update(updatedDTO, ID));
            verify(repository).findById(ID);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(mapper);
        }
    }

    @Nested
    class deleteById {

        @Test
        void givenEntityExists_thenCallsRepository() {
            deleteByIdEntityFound(classUnderTest, new Placement());
        }

        @Test
        void givenEntityDoesNotExits_thenThrowsNoSuchPersistedEntityException() {
            deleteByIdEntityNotFound(classUnderTest);
        }
    }

    private List<Placement> createPlacements() {
        final List<Placement> placements = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            placements.add(createPlacement(i));
        }

        return placements;
    }

    private Placement createPlacement(final Integer id) {
        return Placement.builder()
                .id(id.longValue())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .name("Placement " + id)
                .number(id)
                .build();
    }

    private PlacementDTO toDTO(final Placement placement) {
        return PlacementDTO.builder()
                .id(placement.getId())
                .createdDate(placement.getCreatedDate())
                .name(placement.getName())
                .number(placement.getNumber())
                .build();
    }
}