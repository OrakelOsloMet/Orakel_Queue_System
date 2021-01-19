package com.fredrikpedersen.orakelqueuesystem.dto;

import com.fredrikpedersen.orakelqueuesystem.utilities.annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntityDTO implements DTO {

    private Long id;
    @Required private String name;
    @Required private String subject;
    @Required private int studyYear;
    @Required private boolean digitalConsultation;
    @Required private boolean confirmedDone;
    private String timeConfirmedDone;
}
