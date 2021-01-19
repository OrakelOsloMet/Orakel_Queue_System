package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.Subject;
import com.fredrikpedersen.orakelqueuesystem.dto.SubjectDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.CrudService;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:29
 */

public interface SubjectService extends CrudService<SubjectDTO, Subject, Long> {
    List<SubjectDTO> findSubjectsCurrentSemester();
}
