package com.orakeloslomet.persistance.models.queue;

import com.orakeloslomet.persistance.models.PersistableEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Fredrik Pedersen
 * @version 1.2
 * @since 01/02/2022 at 15:30
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "queue_entities")
@EqualsAndHashCode(callSuper = true)
public class QueueEntity extends PersistableEntity {

    private String name;
    private String subject;

    @ManyToOne(optional = false)
    private Placement placement;

    private String comment;
    private int studyYear;
    private boolean digitalConsultation;

    private Date timeConfirmedDone;

    @Builder
    public QueueEntity(final String name, final String subject, final Placement placement, final String comment,
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
