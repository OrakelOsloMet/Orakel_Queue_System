package com.fredrikpedersen.orakelqueuesystem.utilities.mappers;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.ESemester;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.Subject;
import com.fredrikpedersen.orakelqueuesystem.dto.SubjectDTO;
import org.mapstruct.Mapper;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:54
 */

@Mapper(componentModel = "spring")
public interface SubjectMapper extends IEntityMapper<SubjectDTO, Subject> {

    SubjectDTO toDto(Subject subject);
    Subject toEntity(SubjectDTO subjectDTO);

    default String semesterEnumToString(final ESemester semesterEnum) {return semesterEnum.label;}

    default ESemester semesterStringToEnum(final String semesterString) {
        for (ESemester semesterEnum: ESemester.values()) {
            if (semesterEnum.label.equalsIgnoreCase(semesterString)) {
                return semesterEnum;
            }
        }
        return null; //TODO Throw an error indicating an invalid semester has been passed
    }
}
