package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.repositories.PlacementRepository;
import com.orakeloslomet.utilities.mappers.PlacementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlacementServiceImplTest {

    @Mock
    private PlacementMapper placementMapper;

    @Mock
    private PlacementRepository placementRepository;

    @InjectMocks
    private PlacementServiceImpl placementService;

    @Nested
    class findAll {

        @Test
        void returnsAllFoundEntities() {
            //given
            final List<Placement> domainPlacements = createPlacements();
            given(placementRepository.findAll()).willReturn(domainPlacements);
            domainPlacements.forEach(placement -> given(placementMapper.toDto(placement)).willReturn(toDTO(placement)));

            //when
            final List<PlacementDTO> acutalResults = placementService.findAll();

            //then
            assertEquals(domainPlacements.size(), acutalResults.size());
            for (int i = 0; i < domainPlacements.size(); i++) {
                assertEquals(toDTO(domainPlacements.get(0)), acutalResults.get(0));
            }
            verify(placementRepository).findAll();
            verify(placementMapper, times(domainPlacements.size())).toDto(any(Placement.class));
        }
    }

    @Nested
    class findById {

        private final Long ID = 1L;

        @Test
        void returnsFoundEntity() {
            //given
            final Placement foundById = createPlacement(ID.intValue());
            when(placementRepository.findById(ID)).thenReturn(Optional.of(foundById));
            when(placementMapper.toDto(foundById)).thenReturn(toDTO(foundById));

            //when
            final PlacementDTO actualResult = placementService.findById(ID);

            //then
            assertEquals(toDTO(foundById), actualResult);
            verify(placementRepository).findById(ID);
            verify(placementMapper).toDto(foundById);
        }

        @Test
        void throwsNoSuchElementExceptionIfNotFound() {
            //given
            when(placementRepository.findById(ID)).thenReturn(Optional.empty());

            //when/then
            assertThrows(NoSuchElementException.class, () -> placementService.findById(ID));
            verify(placementRepository).findById(ID);
            verifyNoInteractions(placementMapper);
        }
    }

    @Nested
    class save {

        @Test
        void entityIsMappedAndPassedToRepository() {
            //given
            final Placement domainPlacement = createPlacement(1);
            final PlacementDTO toBeSaved = toDTO(domainPlacement);
            when(placementMapper.toEntity(toBeSaved)).thenReturn(domainPlacement);
            when(placementRepository.save(domainPlacement)).thenReturn(domainPlacement);
            when(placementMapper.toDto(domainPlacement)).thenReturn(toDTO(domainPlacement));

            //when
            final PlacementDTO actualResult = placementService.save(toBeSaved);

            //then
            assertEquals(toBeSaved, actualResult);
            verify(placementMapper).toEntity(toBeSaved);
            verify(placementRepository).save(domainPlacement);
            verify(placementMapper).toDto(domainPlacement);
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
            when(placementRepository.findById(ID)).thenReturn(Optional.of(domainPlacement));
            domainPlacement.setName(UPDATED);
            domainPlacement.setNumber(NUMBER);
            when(placementRepository.save(domainPlacement)).thenReturn(domainPlacement);
            when(placementMapper.toDto(domainPlacement)).thenReturn(updatedDTO);

            //when
            final PlacementDTO actualResult = placementService.update(updatedDTO, ID);

            //then
            assertEquals(updatedDTO, actualResult);
            verify(placementRepository).findById(updatedDTO.getId());
            verify(placementRepository).save(domainPlacement);
            verify(placementMapper).toDto(domainPlacement);
        }

        @Test
        void throwsNoSuchElementExceptionIfNotFound() {
            //given
            final PlacementDTO updatedDTO = toDTO(createPlacement(ID.intValue()));
            when(placementRepository.findById(ID)).thenReturn(Optional.empty());

            //when/then
            assertThrows(EntityNotFoundException.class, () -> placementService.update(updatedDTO, ID));
            verify(placementRepository).findById(ID);
            verifyNoMoreInteractions(placementRepository);
            verifyNoInteractions(placementMapper);
        }
    }

    @Nested
    class deleteById {

        @Test
        void repositoryIsCalled() {
            //given
            final Long id = 1L;
            
            //when
            placementService.deleteById(id);

            verify(placementRepository).deleteById(id);
        }
    }

    private List<Placement> createPlacements() {
        final List<Placement> placements = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
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