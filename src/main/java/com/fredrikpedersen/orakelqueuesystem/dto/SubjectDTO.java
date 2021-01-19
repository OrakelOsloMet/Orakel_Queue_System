package com.fredrikpedersen.orakelqueuesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String name;
    private String semester;

}
