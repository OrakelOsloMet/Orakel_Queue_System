package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.mappers.SubjectMapper;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.repositories.queue.SubjectRepository;
import com.orakeloslomet.services.CrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * @author Fredrik Pedersen
 * @version 1.2
 * @since 08/07/2022 at 19:38
 */

@Slf4j
@Service
public class SubjectServiceImpl extends CrudServiceImpl<SubjectDTO, Subject> implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(final SubjectMapper dtoMapper, final SubjectRepository repository) {
        super(dtoMapper, repository);
        this.subjectRepository = requireNonNull(repository);
    }

    @Override
    public List<SubjectDTO> findSubjectsCurrentSemester() {
        final Month currentMonth = Month.from(ZonedDateTime.now(ZoneId.of("Europe/Paris")));
        final ESemester currentSemester = ESemester.currentSemester(currentMonth);

        return subjectRepository.findAllBySemester(currentSemester).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }
}