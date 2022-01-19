package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 19/01/2022 at 14:49
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

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

}
