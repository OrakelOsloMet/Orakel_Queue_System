package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.PersistableEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 30/09/2021 at 14:24
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "queueentities")
@EqualsAndHashCode(callSuper = true)
public class QueueEntity extends PersistableEntity {

    private String name;
    private String subject;
    private String placement;
    private String comment;
    private int studyYear;
    private boolean digitalConsultation;

    private Date timeConfirmedDone;

    @Builder
    public QueueEntity(final String name, final String subject, final String placement, final String comment,
                       final int studyYear, final boolean digitalConsultation) {
        super();
        this.name = name;
        this.subject = subject;
        this.comment = comment;
        this.placement = placement;
        this.studyYear = studyYear;
        this.digitalConsultation = digitalConsultation;
    }

    public void markAsDone() {
        this.timeConfirmedDone = new Date();
    }
}
