package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import com.orakeloslomet.persistance.repositories.queue.QueueEntityRepository;
import com.orakeloslomet.persistance.repositories.statistics.StatisticsRepository;
import com.orakeloslomet.mappers.QueueEntityMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class QueueEntityServiceImplTest extends CrudServiceTest<QueueEntityDTO, QueueEntity> {

    private final QueueEntityMapper queueEntityMapper;
    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private QueueEntityServiceImpl classUnderTest;

    protected QueueEntityServiceImplTest(@Mock final QueueEntityMapper mapper, @Mock final QueueEntityRepository repository) {
        super(mapper, repository);
        this.queueEntityMapper = mapper;
    }

    @Nested
    class findAll {

        @Test
        void returnsAllFoundEntities() {
            //given
            final List<QueueEntity> queueEntities = createQueueEntities();
            given(repository.findAll()).willReturn(queueEntities);
            queueEntities.forEach(placement -> given(mapper.toDto(placement)).willReturn(toDTO(placement)));

            //when
            final List<QueueEntityDTO> actualResults = classUnderTest.findAll();

            //then
            assertEquals(queueEntities.size(), actualResults.size());
            for (int i = 0; i < queueEntities.size(); i++) {
                assertEquals(toDTO(queueEntities.get(0)), actualResults.get(0));
            }
            verify(repository).findAll();
            verify(mapper, times(queueEntities.size())).toDto(any(QueueEntity.class));
        }
    }

    @Nested
    class findById {

        private final Long ID = 1L;

        @Test
        void returnsFoundEntity() {
            //given
            final QueueEntity foundById = createQueueEntity(ID.intValue());
            when(repository.findById(ID)).thenReturn(Optional.of(foundById));
            when(mapper.toDto(foundById)).thenReturn(toDTO(foundById));

            //when
            final QueueEntityDTO actualResult = classUnderTest.findById(ID);

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
            final QueueEntity domainPlacement = createQueueEntity(1);
            final QueueEntityDTO toBeSaved = toDTO(domainPlacement);
            when(mapper.toEntity(toBeSaved)).thenReturn(domainPlacement);
            setupSaveAndReturnDto(toBeSaved, domainPlacement);

            //when
            final QueueEntityDTO actualResult = classUnderTest.save(toBeSaved);

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

        @Test
        void updatedEntityIsPassedToRepository() {
            //given
            final QueueEntity queueEntity = createQueueEntity(ID.intValue());
            final QueueEntityDTO updatedDTO = QueueEntityDTO.builder()
                    .id(queueEntity.getId())
                    .createdDate(queueEntity.getCreatedDate())
                    .name(UPDATED)
                    .placement(new PlacementDTO())
                    .subject(new SubjectDTO())
                    .comment(UPDATED)
                    .build();
            when(mapper.toEntity(updatedDTO)).thenReturn(queueEntity);
            when(repository.findById(ID)).thenReturn(Optional.of(queueEntity));
            queueEntity.setName(UPDATED);
            queueEntity.setComment(UPDATED);
            setupSaveAndReturnDto(updatedDTO, queueEntity);

            //when
            final QueueEntityDTO actualResult = classUnderTest.update(updatedDTO, ID);

            //then
            assertEquals(updatedDTO, actualResult);
            verify(repository).findById(updatedDTO.getId());
            verifySaveAndReturnDto(queueEntity);
        }

        @Test
        void throwsNoSuchElementExceptionIfNotFound() {
            //given
            final QueueEntityDTO updatedDTO = toDTO(createQueueEntity(ID.intValue()));
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

    @Nested
    class confirmDone {

        @Test
        void dependenciesAreCalled() {
            //given
            final Long id = 1L;
            final QueueEntity queueEntity = createQueueEntity(1);
            final StatisticsEntity statistics = toStatistics(queueEntity);
            when(repository.findById(id)).thenReturn(Optional.of(queueEntity));
            when(queueEntityMapper.toStatistics(queueEntity)).thenReturn(statistics);

            //when
            final Boolean actualResult = classUnderTest.confirmDone(id);

            //then
            assertEquals(true, actualResult);
            verify(repository).findById(id);
            verify(queueEntityMapper).toStatistics(queueEntity);
            verify(statisticsRepository).save(statistics);
            verify(repository).delete(queueEntity);
        }

        @Test
        void throwsNoSuchElementExceptionIfQueueEntityNotFound() {
            //given
            final Long id = 1L;
            when(repository.findById(id)).thenReturn(Optional.empty());

            //when/then
            assertThrows(NoSuchElementException.class, () -> classUnderTest.confirmDone(id));
            verify(repository).findById(id);
            verifyNoInteractions(mapper);
            verifyNoInteractions(statisticsRepository);
            verifyNoMoreInteractions(repository);
        }
    }

    private List<QueueEntity> createQueueEntities() {
        final List<QueueEntity> queueEntities = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            queueEntities.add(createQueueEntity(i));
        }

        return queueEntities;
    }

    private QueueEntity createQueueEntity(final Integer id) {
        return QueueEntity.builder()
                .id(id.longValue())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .name("Fredrik")
                .subject(new Subject())
                .placement(new Placement())
                .comment("Comment")
                .studyYear(1)
                .build();
    }

    private QueueEntityDTO toDTO(final QueueEntity queueEntity) {
        return QueueEntityDTO.builder()
                .id(queueEntity.getId())
                .createdDate(queueEntity.getCreatedDate())
                .name(queueEntity.getName())
                .subject(new SubjectDTO())
                .placement(new PlacementDTO())
                .comment(queueEntity.getComment())
                .studyYear(queueEntity.getStudyYear())
                .build();
    }

    private StatisticsEntity toStatistics(final QueueEntity queueEntity) {
        return StatisticsEntity.builder()
                .id(queueEntity.getId())
                .createdDate(queueEntity.getCreatedDate())
                .subject(queueEntity.getSubject())
                .placement(queueEntity.getPlacement())
                .studyYear(queueEntity.getStudyYear())
                .build();
    }
}