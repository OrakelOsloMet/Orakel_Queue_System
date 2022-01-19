package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.repositories.SubjectRepository;
import com.orakeloslomet.utilities.mappers.SubjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 30/09/2021 at 15:16
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectMapper entityMapper;
    private final SubjectRepository repository;

    @Override
    public List<SubjectDTO> findSubjectsCurrentSemester() {
        final ESemester currentSemester = this.determineSemester();
        return mapToDtos(repository.findAllBySemester(currentSemester));
    }

    @Override
    public List<SubjectDTO> findAll() {
        return mapToDtos(repository.findAll());
    }

    public SubjectDTO findById(final Long id) {
        return repository.findById(id)
                .map(entityMapper::toDto)
                .orElseThrow(); //TODO Improve error handling
    }

    @Override
    public SubjectDTO save(final SubjectDTO subjectDTO) {
        return saveAndReturnDto(entityMapper.toEntity(subjectDTO));
    }

    @Override
    @Transactional
    public SubjectDTO update(final SubjectDTO subjectDTO, final Long id) {
        return repository.findById(id)
                .map(subject -> {
                    subject.setName(subjectDTO.getName());
                    subject.setSemester(entityMapper.semesterStringToEnum(subjectDTO.getSemester()));
                    return saveAndReturnDto(subject);
                }).orElseThrow(() -> new EntityNotFoundException(String.format("Subject with ID %s not found!", id)));
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    private SubjectDTO saveAndReturnDto(final Subject subject) {
        final Subject savedEntity = repository.save(subject);
        return entityMapper.toDto(savedEntity);
    }

    private List<SubjectDTO> mapToDtos(final List<Subject> queueEntities) {
        return queueEntities.stream().map(entityMapper::toDto).collect(Collectors.toList());
    }

    private ESemester determineSemester() {
        final EnumSet<Month> spring = EnumSet.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE);
        final Month currentMonth = Month.from(ZonedDateTime.now(ZoneId.of("Europe/Paris")));

        if (spring.contains(currentMonth))
            return ESemester.SPRING;

        return ESemester.AUTUMN;
    }
}