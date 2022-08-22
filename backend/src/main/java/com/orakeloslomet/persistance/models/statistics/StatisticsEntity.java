package com.orakeloslomet.persistance.models.statistics;

import com.orakeloslomet.persistance.models.PersistableEntity;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.models.queue.Subject;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Fredrik Pedersen
 * @since 01/02/2022 at 15:15
 */

@Setter
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "queue_statistics")
@EqualsAndHashCode(callSuper = true)
public class StatisticsEntity extends PersistableEntity {

    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToOne(optional = false)
    private Placement placement;

    private int studyYear;

    public StatisticsEntity(final Subject subject, final Placement placement, final int studyYear) {
        super();
        this.subject = subject;
        this.placement = placement;
        this.studyYear = studyYear;
    }
}
