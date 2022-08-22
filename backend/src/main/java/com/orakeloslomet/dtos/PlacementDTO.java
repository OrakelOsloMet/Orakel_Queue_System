package com.orakeloslomet.dtos;

import com.orakeloslomet.utilities.annotations.Required;
import lombok.*;

import java.sql.Timestamp;

/**
 * @author Fredrik Pedersen
 * @since 30/09/2021 at 15:39
 */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlacementDTO implements DTO {

    private Long id;
    private Timestamp createdDate;
    @Required private String name;
    @Required private int number;
}
