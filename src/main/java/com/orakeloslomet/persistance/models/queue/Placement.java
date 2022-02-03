package com.orakeloslomet.persistance.models.queue;

import com.orakeloslomet.persistance.models.PersistableEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 14:08
 */

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "placements")
@EqualsAndHashCode(callSuper = true)
public class Placement extends PersistableEntity {

    private String name;
    private Integer number;

    public Placement(final String name, final Integer number) {
        super();
        this.name = name;
        this.number = number;
    }
}
