package com.fredrikpedersen.orakelqueuesystem.utilities.mappers;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.ESubject;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 22:13
 */

@Mapper(componentModel = "spring")
public abstract class QueueEntityMapper {

    public abstract QueueEntity queueEntityDTOToQueueEntity(final QueueEntityDTO queueEntityDTO);

    @Mapping(target = "url", source = "")
    public abstract QueueEntityDTO queueEntityToQueueEntityDTO(final QueueEntity queueEntity);

    String subjectEnumToString(ESubject subjectEnum) {
        return subjectEnum.label;
    }

    ESubject subjectStringToEnum(String subjectString) {
        for(ESubject subjectEnum : ESubject.values()) {
            if(subjectEnum.label.equals(subjectString)) {
                return subjectEnum;
            }
        }
        return null;
    }
}
