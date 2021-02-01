package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.DomainEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:31
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "queueentities")
public class QueueEntity implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String subject;

    private int studyYear;
    private boolean digitalConsultation;
    private boolean confirmedDone;
    private Date timeConfirmedDone;

    //TODO Remove confirmedDone as an attribute, you only need to check if timeConfirmedDone is set, numbnut.
    public QueueEntity(final String name, final String subject, final int studyYear, final boolean digitalConsultation) {
        this.name = name;
        this.subject = subject;
        this.studyYear = studyYear;
        this.digitalConsultation = digitalConsultation;
        this.confirmedDone = false;
    }

    //TODO Remove this once confirmedDone attribute is removed
    public void markAsDone() {
        this.confirmedDone = true;
        this.timeConfirmedDone = new Date();
    }
}
