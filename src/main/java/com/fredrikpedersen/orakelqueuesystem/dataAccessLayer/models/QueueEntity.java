package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 21:32
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "queueentities")
public class QueueEntity implements DomainEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String subject;
    private int studyYear;
    private boolean digitalConsultation;

    public QueueEntity(final String firstname, final String subject, final int studyYear, final boolean digitalConsultation) {
        this.firstname = firstname;
        this.subject = subject;
        this.studyYear = studyYear;
        this.digitalConsultation = digitalConsultation;
    }
}
