package com.orakeloslomet.dtos;

import com.orakeloslomet.utilities.annotations.Required;
import lombok.*;

import java.sql.Timestamp;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:47
 */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO implements DTO {

    private Long id;
    private Timestamp createdDate;
    @Required private String name;
    @Required private String semester;
}
