package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.services.CrudService;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:29
 */

public interface SubjectService extends CrudService<SubjectDTO> {
    List<SubjectDTO> findSubjectsCurrentSemester();
}
