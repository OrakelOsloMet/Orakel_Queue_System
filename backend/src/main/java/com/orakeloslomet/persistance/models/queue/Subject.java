package com.orakeloslomet.persistance.models.queue;

import com.orakeloslomet.persistance.models.PersistableEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 30/09/2021 at 14:24
 */


@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "subjects")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Subject extends PersistableEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private ESemester semester;

    public Subject(final String name, final ESemester semester) {
        super();
        this.name = name;
        this.semester = semester;
    }

}
