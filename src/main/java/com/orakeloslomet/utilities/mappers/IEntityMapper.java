package com.orakeloslomet.utilities.mappers;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

public interface IEntityMapper<DTO, ENTITY> {

    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
