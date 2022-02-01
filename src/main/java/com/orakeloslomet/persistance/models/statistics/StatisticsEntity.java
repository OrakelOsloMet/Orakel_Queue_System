package com.orakeloslomet.persistance.models.statistics;

import com.orakeloslomet.persistance.models.PersistableEntity;
import com.orakeloslomet.persistance.models.queue.Placement;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Fredrik Pedersen
 * @since 01/02/2022 at 15:15
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "queue_statistics")
@EqualsAndHashCode(callSuper = true)
public class StatisticsEntity extends PersistableEntity {

    private String subject;

    @ManyToOne(optional = false)
    private Placement placement;

    private int studyYear;
    private boolean digitalConsultation;

    private Date timeConfirmedDone;

    @Builder
    public StatisticsEntity(final String subject, final Placement placement,
                       final int studyYear, final boolean digitalConsultation, final Date timeConfirmedDone) {
        super();
        this.subject = subject;
        this.placement = placement;
        this.studyYear = studyYear;
        this.digitalConsultation = digitalConsultation;
        this.timeConfirmedDone = timeConfirmedDone;
    }
}
