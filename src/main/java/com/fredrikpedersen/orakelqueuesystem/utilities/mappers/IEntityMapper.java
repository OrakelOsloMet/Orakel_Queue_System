package com.fredrikpedersen.orakelqueuesystem.utilities.mappers;

public interface IEntityMapper<DTO, ENTITY> {

    DTO toDto(ENTITY enity);
    ENTITY toEntity(DTO dto);
}
