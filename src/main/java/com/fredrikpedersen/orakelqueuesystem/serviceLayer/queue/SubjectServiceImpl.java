package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.ESemester;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.Subject;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.SubjectRepository;
import com.fredrikpedersen.orakelqueuesystem.dto.SubjectDTO;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import com.fredrikpedersen.orakelqueuesystem.utilities.mappers.SubjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:45
 */

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(final SubjectMapper subjectMapper, final SubjectRepository subjectRepository) {
        this.subjectMapper = subjectMapper;
        this.subjectRepository = subjectRepository;
    }

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

    @Override
    public SubjectDTO createNew(final SubjectDTO subjectDTO) {
        return saveAndReturnDTO(subjectMapper.toEntity(subjectDTO));
    }

    @Override
    public void deleteById(final Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public SubjectDTO saveAndReturnDTO(final Subject subject) {
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