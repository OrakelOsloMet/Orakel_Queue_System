package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.mappers.SubjectMapper;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.repositories.queue.SubjectRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SubjectServiceImplTest extends CrudServiceTest<SubjectDTO, Subject> {

    private final SubjectMapper mapper;
    private final SubjectRepository repository;

    @InjectMocks
    private SubjectServiceImpl classUnderTest;

    protected SubjectServiceImplTest(@Mock final SubjectMapper mapper, @Mock final SubjectRepository repository) {
        super(mapper, repository);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Nested
    class findAll {

        @Test
        void returnsAllFoundEntities() {
            //given
            final List<Subject> domainSubjects = createSubjects();
            given(repository.findAll()).willReturn(domainSubjects);
            domainSubjects.forEach(subject -> given(mapper.toDto(subject)).willReturn(toDTO(subject)));

            //when
            final List<SubjectDTO> actualResults = classUnderTest.findAll();

            //then
            assertEquals(domainSubjects.size(), actualResults.size());
            for (int i = 0; i < domainSubjects.size(); i++) {
                assertEquals(toDTO(domainSubjects.get(0)), actualResults.get(0));
            }
            verify(repository).findAll();
            verify(mapper, times(domainSubjects.size())).toDto(any(Subject.class));
        }
    }

    @Nested
    class findById {

        private final Long ID = 1L;

        @Test
        void returnsFoundEntity() {
            //given
            final Subject foundById = createSubject(ID.intValue());
            when(repository.findById(ID)).thenReturn(Optional.of(foundById));
            when(mapper.toDto(foundById)).thenReturn(toDTO(foundById));

            //when
            final SubjectDTO actualResult = classUnderTest.findById(ID);

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
            final Subject domainSubject = createSubject(1);
            final SubjectDTO toBeSaved = toDTO(domainSubject);
            when(mapper.toEntity(toBeSaved)).thenReturn(domainSubject);
            setupSaveAndReturnDto(toBeSaved, domainSubject);

            //when
            final SubjectDTO actualResult = classUnderTest.save(toBeSaved);

            //then
            assertEquals(toBeSaved, actualResult);
            verify(mapper).toEntity(toBeSaved);
            verifySaveAndReturnDto(domainSubject);
        }
    }

    @Nested
    class update {

        private final Long ID = 1L;
        private final String UPDATED = "updated";
        private final ESemester SEMESTER_UPDATED = ESemester.SPRING;

        @Test
        void updatedEntityIsPassedToRepository() {
            //given
            final Subject domainSubject = createSubject(ID.intValue());
            final SubjectDTO updatedDTO = SubjectDTO.builder()
                    .id(domainSubject.getId())
                    .createdDate(domainSubject.getCreatedDate())
                    .name(UPDATED)
                    .semester(SEMESTER_UPDATED.name())
                    .build();
            when(repository.findById(ID)).thenReturn(Optional.of(domainSubject));
            when(mapper.toEntity(updatedDTO)).thenReturn(domainSubject);
            domainSubject.setName(UPDATED);
            domainSubject.setSemester(SEMESTER_UPDATED);
            setupSaveAndReturnDto(updatedDTO, domainSubject);

            //when
            final SubjectDTO actualResult = classUnderTest.update(updatedDTO, ID);

            //then
            assertEquals(updatedDTO, actualResult);
            verify(repository).findById(updatedDTO.getId());
            verifySaveAndReturnDto(domainSubject);
        }

        @Test
        void throwsNoSuchElementExceptionIfNotFound() {
            //given
            final SubjectDTO updatedDTO = toDTO(createSubject(ID.intValue()));
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
    class findSubjectsCurrentSemester {

        @Test
        void callsRepository() {
            //given
            final Month currentMonth = Month.from(ZonedDateTime.now(ZoneId.of("Europe/Paris")));
            final ESemester expectedSemester = ESemester.currentSemester(currentMonth);
            final List<Subject> domainSubjects = createSubjects();
            when(repository.findAllBySemester(expectedSemester)).thenReturn(domainSubjects);
            domainSubjects.forEach(subject -> when(mapper.toDto(subject)).thenReturn(toDTO(subject)));

            //when
            final List<SubjectDTO> actualResults = classUnderTest.findSubjectsCurrentSemester();

            //then
            for (int i = 0; i < domainSubjects.size(); i++) {
                assertEquals(toDTO(domainSubjects.get(0)), actualResults.get(0));
            }
            verify(repository).findAllBySemester(expectedSemester);
            verify(mapper, times(domainSubjects.size())).toDto(any(Subject.class));
        }
    }
    
    private List<Subject> createSubjects() {
        final List<Subject> subjects = new ArrayList<>();
        
        for (int i = 1; i <= 10; i++) {
            subjects.add(createSubject(i));
        }
        
        return subjects;
    }
    
    private Subject createSubject(final Integer id) {
        return Subject.builder()
                .id(id.longValue())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .name("Subject " + id)
                .semester(ESemester.AUTUMN)
                .build();
    }

    private SubjectDTO toDTO(final Subject subject) {
        return SubjectDTO.builder()
                .id(subject.getId())
                .createdDate(subject.getCreatedDate())
                .name(subject.getName())
                .semester(subject.getSemester().name())
                .build();
    }

}