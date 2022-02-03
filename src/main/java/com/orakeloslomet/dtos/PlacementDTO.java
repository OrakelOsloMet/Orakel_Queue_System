package com.orakeloslomet.dtos;

import com.orakeloslomet.utilities.annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 15:39
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlacementDTO implements DTO {

    private Long id;
    @Required private String name;
    @Required private int number;
}
