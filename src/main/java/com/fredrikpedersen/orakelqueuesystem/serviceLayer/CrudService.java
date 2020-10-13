package com.fredrikpedersen.orakelqueuesystem.serviceLayer;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.DomainEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.DTO;

import java.util.List;

public interface CrudService<T extends DTO, S extends DomainEntity, ID extends Long> {

    List<T> findAll();
    T createNew(T dtoObject);
    void delete(T dtoObject);
    void deleteById(ID id);

    T saveAndReturnDTO(final S domainObject);
}
