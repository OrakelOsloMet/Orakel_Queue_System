package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.repositories.SubjectRepository;
import com.orakeloslomet.utilities.mappers.SubjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;

    @Override
    public List<SubjectDTO> findSubjectsCurrentSemester() {
        String currentSemester = this.determineSemester().label;

        return this.findAll().stream()
                .filter(subject -> subject.getSemester().equalsIgnoreCase(currentSemester))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectDTO> findAll() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
    }

    public SubjectDTO findById(final Long id) {
        return subjectRepository.findById(id)
                .map(subjectMapper::toDto)
                .orElseThrow(RuntimeException::new); //TODO Improve error handling
    }

    @Override
    public SubjectDTO save(final SubjectDTO subjectDTO) {
        return saveAndReturnDto(subjectMapper.toEntity(subjectDTO));
    }

    @Override
    public SubjectDTO update(final SubjectDTO subjectDTO, final Long id) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setName(subjectDTO.getName());
                    subject.setSemester(subjectMapper.semesterStringToEnum(subjectDTO.getSemester()));
                    return saveAndReturnDto(subject);
                }).orElseThrow(() -> new EntityNotFoundException(String.format("Subject with ID %s not found!", id)));
    }

    @Override
    public void deleteById(final Long id) {
        subjectRepository.deleteById(id);
    }

    private SubjectDTO saveAndReturnDto(final Subject subject) {
        Subject savedEntity = subjectRepository.save(subject);
        return subjectMapper.toDto(savedEntity);
    }

    private ESemester determineSemester() {
        EnumSet<Month> spring = EnumSet.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        Month currentMonth = Month.from(zdt);

        if (spring.contains(currentMonth)) return ESemester.SPRING;

        return ESemester.AUTUMN;
    }
}