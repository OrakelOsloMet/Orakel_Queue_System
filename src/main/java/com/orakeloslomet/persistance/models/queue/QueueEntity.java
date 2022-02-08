package com.orakeloslomet.persistance.models.queue;

import com.orakeloslomet.persistance.models.PersistableEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToOne(optional = false)
    private Placement placement;

    private String comment;
    private int studyYear;

    @Builder
    public QueueEntity(final String name, final Subject subject, final Placement placement, final String comment,
                       final int studyYear) {
        super();
        this.name = name;
        this.subject = subject;
        this.comment = comment;
        this.placement = placement;
        this.studyYear = studyYear;
    }
}
