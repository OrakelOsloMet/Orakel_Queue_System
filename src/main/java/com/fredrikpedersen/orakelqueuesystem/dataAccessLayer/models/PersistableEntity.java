package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 14:09
 */

@Setter
@Getter
@ToString
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersistableEntity implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
