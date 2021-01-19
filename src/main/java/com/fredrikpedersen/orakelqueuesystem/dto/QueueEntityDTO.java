package com.fredrikpedersen.orakelqueuesystem.dto;

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
    private String url;
    private String name;
    private String subject;
    private int studyYear;
    private boolean digitalConsultation;
    private boolean confirmedDone;
    private String timeConfirmedDone;
}
