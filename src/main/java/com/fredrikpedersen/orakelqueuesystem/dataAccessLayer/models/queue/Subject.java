package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.DomainEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:20
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ESemester semester;

    public Subject(final String name, final ESemester semester) {
        this.name = name;
        this.semester = semester;
    }

}
