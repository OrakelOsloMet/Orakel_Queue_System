package com.orakeloslomet.dtos;

import com.orakeloslomet.utilities.annotations.Required;
import lombok.*;

import java.sql.Timestamp;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 30/09/2021 at 14:32
 */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntityDTO implements DTO {

    private Long id;
    private Timestamp createdDate;
    @Required private String name;
    @Required private SubjectDTO subject;
    private PlacementDTO placement;
    private String comment;
    @Required private int studyYear;
}
