package com.fredrikpedersen.orakelqueuesystem.utilities.mappers;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.ESubject;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QueueEntityMapper extends IEntityMapper<QueueEntityDTO, QueueEntity> {

    @Mapping(target = "url", source = "")
    QueueEntityDTO toDto(QueueEntity queueEntity);

    QueueEntity toEntity(QueueEntityDTO queueEntityDTO);

    default String subjectEnumToString(ESubject subjectEnum) {
        return subjectEnum.label;
    }

    default ESubject subjectStringToEnum(final String subjectString) {
        for (ESubject subjectEnum : ESubject.values()) {
            if (subjectEnum.label.equals(subjectString)) {
                return subjectEnum;
            }
        }
        return null;
    }
}
