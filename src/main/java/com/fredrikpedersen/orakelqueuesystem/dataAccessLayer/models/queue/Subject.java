package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.PersistableEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@Table(name = "subjects")
@EqualsAndHashCode(callSuper = true)
public class Subject extends PersistableEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private ESemester semester;

    @Builder
    public Subject(final String name, final ESemester semester) {
        super();
        this.name = name;
        this.semester = semester;
    }

}
