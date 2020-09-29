package com.fredrikpedersen.orakelqueuesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 22:07
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntityDTO implements DTO {

    private Long id;
    private String url;
    private String name;
    private String subject;
    private int studyYear;
    private boolean digitalConsultation;
    private boolean confirmedDone;
    private String timeConfirmedDone;
}
