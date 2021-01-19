package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:42
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
