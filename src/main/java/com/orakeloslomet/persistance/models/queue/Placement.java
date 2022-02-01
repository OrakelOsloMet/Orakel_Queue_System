package com.orakeloslomet.persistance.models.queue;

import com.orakeloslomet.persistance.models.PersistableEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 14:08
 */


@Data
@Entity
@NoArgsConstructor
@Table(name = "placements")
@EqualsAndHashCode(callSuper = true)
public class Placement extends PersistableEntity {

    private String prefix;
    private Integer placements;

    @Builder
    public Placement(final String prefix, final Integer placements) {
        super();
        this.prefix = prefix;
        this.placements = placements;
    }
}
