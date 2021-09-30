package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.ESemester;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.Subject;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.SubjectRepository;
import com.fredrikpedersen.orakelqueuesystem.dtos.SubjectDTO;
import com.fredrikpedersen.orakelqueuesystem.utilities.mappers.SubjectMapper;
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
    public SubjectDTO createNew(final SubjectDTO subjectDTO) {
        return saveAndReturnDTO(subjectMapper.toEntity(subjectDTO));
    }

    @Override
    public SubjectDTO edit(final SubjectDTO subjectDTO, final Long id) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setName(subjectDTO.getName());
                    subject.setSemester(subjectMapper.semesterStringToEnum(subjectDTO.getSemester()));
                    return saveAndReturnDTO(subject);
                }).orElseThrow(() -> new EntityNotFoundException(String.format("Subject with ID %s not found!", id)));
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