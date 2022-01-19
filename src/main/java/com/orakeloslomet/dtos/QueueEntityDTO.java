package com.orakeloslomet.dtos;

import com.orakeloslomet.utilities.annotations.Required;
import lombok.*;

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
    @Required
    private String name;
    @Required private String subject;
    @Required private String placement;
    private String comment;
    @Required private int studyYear;
    @Required private boolean digitalConsultation;
    private String timeConfirmedDone;
}
