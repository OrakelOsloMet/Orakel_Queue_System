package com.fredrikpedersen.orakelqueuesystem.utilities.mappers;

public interface IEntityMapper<DTO, ENTITY> {

    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
