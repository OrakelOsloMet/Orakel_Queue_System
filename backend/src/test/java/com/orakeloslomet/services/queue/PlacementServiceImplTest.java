package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.mappers.PlacementMapper;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.repositories.queue.PlacementRepository;
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

class PlacementServiceImplTest extends CrudServiceTest<PlacementDTO, Placement> {

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

        private final Long ID = 1L;

        @Test
        void returnsFoundEntity() {
            //given
            final Placement foundById = createPlacement(ID.intValue());
            when(repository.findById(ID)).thenReturn(Optional.of(foundById));
            when(mapper.toDto(foundById)).thenReturn(toDTO(foundById));

            //when
            final PlacementDTO actualResult = classUnderTest.findById(ID);

            //then
            assertEquals(toDTO(foundById), actualResult);
            verify(repository).findById(ID);
            verify(mapper).toDto(foundById);
        }

        @Test
        void throwsNoSuchElementExceptionIfNotFound() {
            //given
            when(repository.findById(ID)).thenReturn(Optional.empty());

            //when/then
            assertThrows(NoSuchElementException.class, () -> classUnderTest.findById(ID));
            verify(repository).findById(ID);
            verifyNoInteractions(mapper);
        }
    }

    @Nested
    class save {

        @Test
        void entityIsMappedAndPassedToRepository() {
            //given
            final Placement domainPlacement = createPlacement(1);
            final PlacementDTO toBeSaved = toDTO(domainPlacement);
            when(mapper.toEntity(toBeSaved)).thenReturn(domainPlacement);
            setupSaveAndReturnDto(toBeSaved, domainPlacement);

            //when
            final PlacementDTO actualResult = classUnderTest.save(toBeSaved);

            //then
            assertEquals(toBeSaved, actualResult);
            verify(mapper).toEntity(toBeSaved);
            verifySaveAndReturnDto(domainPlacement);
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
        void throwsNoSuchElementExceptionIfNotFound() {
            //given
            final PlacementDTO updatedDTO = toDTO(createPlacement(ID.intValue()));
            when(repository.findById(ID)).thenReturn(Optional.empty());

            //when/then
            assertThrows(NoSuchElementException.class, () -> classUnderTest.update(updatedDTO, ID));
            verify(repository).findById(ID);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(mapper);
        }
    }

    @Nested
    class deleteById {

        @Test
        void repositoryIsCalled() {
            //given
            final Long id = 1L;

            //when
            classUnderTest.deleteById(id);

            //then
            verify(repository).deleteById(id);
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