package com.orakeloslomet.dtos;

import com.orakeloslomet.utilities.annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:47
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO implements DTO {

    private Long id;
    private Timestamp createdDate;
    @Required private String name;
    @Required private String semester;
}
