package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.DomainEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "queueentities")
public class QueueEntity implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ESubject subject;

    private int studyYear;
    private boolean digitalConsultation;
    private boolean confirmedDone;
    private Date timeConfirmedDone;

    public QueueEntity(final String name, final ESubject subject, final int studyYear, final boolean digitalConsultation) {
        this.name = name;
        this.subject = subject;
        this.studyYear = studyYear;
        this.digitalConsultation = digitalConsultation;
        this.confirmedDone = false;
    }
}
